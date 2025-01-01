package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.auditing.ApplicationAuditAware;
import com.ptit.EnglishExplorer.config.Config;
import com.ptit.EnglishExplorer.config.VNPAYConfig;
import com.ptit.EnglishExplorer.data.entity.Transaction;
import com.ptit.EnglishExplorer.data.entity.User;
import com.ptit.EnglishExplorer.data.entity.Wallet;
import com.ptit.EnglishExplorer.data.repository.TransactionRepository;
import com.ptit.EnglishExplorer.data.service.TransactionService;
import com.ptit.EnglishExplorer.data.service.WalletService;
import com.ptit.EnglishExplorer.data.types.TransactionStatus;
import com.ptit.EnglishExplorer.utils.VNPayUtil;
import com.ptit.EnglishExplorer.vnpay.PaymentDTO;
import com.ptit.EnglishExplorer.vnpay.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TransactionServiceImpl extends BaseServiceImpl<Transaction, Long, TransactionRepository> implements TransactionService {

    @Autowired
    private WalletService walletService;

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private VNPAYConfig vnpayConfig;
    @Autowired
    public TransactionServiceImpl(TransactionRepository repository) {
        super(repository);
    }

    @Override
    public List<Transaction> findByUserId(Long id) {
        return repository.findByUserId(id);
    }

    @Override
    @Transactional
    public PaymentDTO.VNPayResponse payment(Long amount, HttpServletRequest request) {
        long finalAmount = amount;  // Nhân với 1L là không thay đổi, bạn có thể điều chỉnh theo logic của mình
        User user = ApplicationAuditAware.getCurrentUser();

        // Tạo transaction và lưu vào cơ sở dữ liệu
        Transaction transaction = new Transaction();
        transaction.setAmount(finalAmount);
        transaction.setUser(user);
        transaction.setStatus(TransactionStatus.PENDING);

        Wallet wallet = null;
        if(user!=null && user.getId()!=null) {
            wallet = walletService.findByUser(user.getId());
        }

        if(wallet==null) {
            wallet = new Wallet();
            wallet.setUser(user);
            walletService.save(wallet);
        }

        // Tạo timestamp và gán vào ref của transaction
        String timestamp = String.valueOf(System.currentTimeMillis());
        transaction.setRef(timestamp);

        transaction.setWallet(wallet);
        // Lưu giao dịch vào cơ sở dữ liệu (giả sử bạn đã có phương thức save)
        Transaction savedTransaction = repository.save(transaction);

        // Gọi service để tạo thanh toán VNPay
        PaymentDTO.VNPayResponse vnPayResponse = paymentService.createVnPayPayment(finalAmount, request, timestamp);

        // Trả về URL thanh toán và thông tin giao dịch
        return vnPayResponse;
    }

    @Override
    @Transactional
    public String callback(Map<String, String> params) {
        // Lấy các tham số từ VNPay
        String vnpTxnRef = params.get("vnp_TxnRef"); // Mã giao dịch từ VNPay (không dùng trong tìm kiếm giao dịch)
        String vnpOrderInfo = params.get("vnp_OrderInfo"); // Thông tin đơn hàng
        String vnpResponseCode = params.get("vnp_ResponseCode"); // Mã phản hồi từ VNPay
        String vnpSecureHash = params.get("vnp_SecureHash"); // Chữ ký// Key bí mật của bạn

        // Kiểm tra chữ ký (chống giả mạo)
        boolean isValidSignature = VNPayUtil.verifyVNPayPayment(params, vnpayConfig.getSecretKey());
        if (!isValidSignature) {
            // Nếu chữ ký không hợp lệ, trả về URL thất bại
            return "http://localhost:3000/payment/invalid";
        }

        // Tìm giao dịch trong cơ sở dữ liệu từ thông tin đơn hàng (vnpOrderInfo)
        Transaction transaction = repository.findByRef(vnpOrderInfo);
        if (transaction == null) {
            // Nếu không tìm thấy giao dịch, trả về URL thất bại
            return "http://localhost:3000/payment/failure";
        }

        // Kiểm tra trạng thái giao dịch đã xử lý chưa
        if (transaction.getStatus() != TransactionStatus.PENDING) {
            // Nếu giao dịch đã được xử lý rồi, trả về kết quả thành công mà không xử lý lại
            return "http://localhost:3000/payment/success?"+"transactionId=" + transaction.getId();
        }

        // Xử lý kết quả thanh toán từ VNPay
        if ("00".equals(vnpResponseCode)) { // Mã "00" là thanh toán thành công
            transaction.setStatus(TransactionStatus.SUCCESS);

            // Cộng tiền vào ví của người dùng
            Wallet wallet = transaction.getWallet();
            wallet.setBalance(wallet.getBalance() + transaction.getAmount());
            walletService.save(wallet); // Lưu lại ví sau khi cộng tiền

            // Lưu lại thông tin giao dịch đã cập nhật vào cơ sở dữ liệu
            repository.save(transaction);

            // Trả về URL chuyển hướng tới client (thành công)
            return "http://localhost:3000/payment/success?"+"transactionId=" + transaction.getId();
        } else {
            // Nếu thanh toán thất bại
            transaction.setStatus(TransactionStatus.FAILED);

            // Lưu lại trạng thái thất bại của giao dịch
            repository.save(transaction);

            // Trả về URL chuyển hướng tới client (thất bại)
            return "http://localhost:3000/payment/failure";
        }
    }


    @Scheduled(fixedRate = 60000)  // Chạy mỗi phút
    public void checkPendingTransactions() {
        long currentTime = System.currentTimeMillis();
        long timeout = 15 * 60 * 1000; // 30 phút tính theo mili giây

        // Lấy tất cả giao dịch với trạng thái PENDING và thời gian tạo quá 30 phút
        List<Transaction> pendingTransactions = repository.findByStatusAndCreatedAtBefore(
                TransactionStatus.PENDING, currentTime - timeout);

        // Cập nhật trạng thái thành CANCEL
        for (Transaction transaction : pendingTransactions) {
            transaction.setStatus(TransactionStatus.FAILED);  // Đổi trạng thái thành CANCELLED
            repository.save(transaction);  // Lưu lại thay đổi
        }
    }

    @Override
    public Page<Transaction> findList(Pageable pageable) {
        // Sort transactions by createdAt in descending order to get the most recent first
        Pageable sortedByDate = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.desc("createdAt")));
        return repository.findAll(sortedByDate);  // Use the sorted pageable for the query
    }


}
