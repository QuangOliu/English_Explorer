package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.auditing.ApplicationAuditAware;
import com.ptit.EnglishExplorer.data.entity.Transaction;
import com.ptit.EnglishExplorer.data.entity.User;
import com.ptit.EnglishExplorer.data.service.TransactionService;
import com.ptit.EnglishExplorer.data.service.impl.WalletServiceImpl;
import com.ptit.EnglishExplorer.vnpay.PaymentDTO;
import com.ptit.EnglishExplorer.vnpay.PaymentService;
import com.ptit.EnglishExplorer.vnpay.ResponseObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/transactions")
public class TransactionController extends BaseController<Transaction, Long, TransactionService> {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private WalletServiceImpl walletServiceImpl;

    @Autowired
    public TransactionController(TransactionService service) {
        super(service);
    }

    @GetMapping("/vn-pay/{amount}")
    public ResponseEntity<PaymentDTO.VNPayResponse> pay(@PathVariable Long amount, HttpServletRequest request) {
            return ResponseObject.status(HttpStatus.OK).body(service.payment(amount, request));
    }

    // API để lấy danh sách giao dịch của người dùng hiện tại
    @GetMapping("/my-transactions")
    public ResponseEntity<List<Transaction>> getMyTransactions() {
        // Lấy người dùng hiện tại từ auditing
        User user = ApplicationAuditAware.getCurrentUser();

        // Kiểm tra nếu người dùng không tồn tại
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Lấy danh sách giao dịch của người dùng
        List<Transaction> transactions = service.findByUserId(user.getId());

        if (transactions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Trả về 204 nếu không có giao dịch nào
        }

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/callback")
    @Transactional
    public void handleVNPayCallback(@RequestParam Map<String, String> params, HttpServletResponse response) throws IOException {
        // Gọi phương thức callback từ service và nhận lại URL chuyển hướng
        String redirectUrl = service.callback(params);

        // Thực hiện chuyển hướng đến URL mà service trả về
        response.sendRedirect(redirectUrl);
    }
}
