package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.auditing.ApplicationAuditAware;
import com.ptit.EnglishExplorer.config.Config;
import com.ptit.EnglishExplorer.config.VNPAYConfig;
import com.ptit.EnglishExplorer.data.entity.*;
import com.ptit.EnglishExplorer.data.service.*;
import com.ptit.EnglishExplorer.data.service.impl.UserServiceImpl;
import com.ptit.EnglishExplorer.utils.VNPayUtil;
import com.ptit.EnglishExplorer.vnpay.PaymentDTO;
import com.ptit.EnglishExplorer.vnpay.PaymentService;
import com.ptit.EnglishExplorer.vnpay.ResponseObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/classrooms")
public class ClassroomController extends BaseController<Classroom, Long, ClassroomService> {
    @Autowired
    private VNPAYConfig vnpayConfig;
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    public ClassroomController(ClassroomService service) {
        super(service);
    }

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ClassMemberService classmemberService;

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentHistoryService paymentHistoryService;

    @GetMapping("/vn-pay/{id}")
    public ResponseObject<PaymentDTO.VNPayResponse> pay(@PathVariable Long id, HttpServletRequest request) {
        Classroom classroom = service.findById(id).orElse(null);
        if (classroom != null) {
            long cost = classroom.getCost();
            User user = ApplicationAuditAware.getCurrentUser();
            if (user != null) {
                String orderInfo = "classroomId:" + id + ",userId:" + user.getId(); // Gắn classroomId và userId vào orderInfo
                return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(cost, request, orderInfo));
            }
            return new ResponseObject<>(HttpStatus.UNAUTHORIZED, "User not authenticated", null);
        }
        return new ResponseObject<>(HttpStatus.NOT_FOUND, "Classroom not found", null);
    }

    @GetMapping("/vn-pay-callback")
    @Transactional
    public void vnPayCallback(@RequestParam Map<String, String> params, HttpServletResponse response) throws IOException {
        // Kiểm tra tính hợp lệ của giao dịch VNPay
        boolean isPaymentValid = VNPayUtil.verifyVNPayPayment(params, vnpayConfig.getSecretKey());

        if (isPaymentValid) {
            String orderId = params.get("vnp_TxnRef");
            String paymentStatus = params.get("vnp_ResponseCode");

            // Kiểm tra trạng thái thanh toán
            if ("00".equals(paymentStatus)) {
                // Lấy orderInfo từ params và tách classroomId và timestamp
                String orderInfo = params.get("vnp_OrderInfo"); // orderInfo chứa classroomId và timestamp
                String[] orderInfoParts = orderInfo.split(","); // Tách các phần trong orderInfo

                // Lấy classroomId và timestamp từ orderInfo
                Long classroomId = Long.valueOf(orderInfoParts[0].split(":")[1]);  // Ví dụ: classroomId:12345
                Long userId = Long.valueOf(orderInfoParts[0].split(":")[1]);  // Ví dụ: classroomId:12345
                Long timestamp = Long.valueOf(orderInfoParts[1].split(":")[1]);  // Ví dụ: timestamp:1672547890000

                // Kiểm tra tính hợp lệ của timestamp (ví dụ: không quá 5 phút so với hiện tại)
                long currentTime = System.currentTimeMillis();
                // Tìm classroom từ database
                Classroom classroom = service.findById(classroomId).orElse(null);
                User user = userService.findById(userId).orElse(null);
                if (classroom != null && user != null) {
                    // Tạo đối tượng ClassMember và lưu vào database
                    ClassMember classMember = new ClassMember();

                    classMember.setClassroom(classroom);
                    classMember.setUser(user);
                    classmemberService.save(classMember);

                    // Lưu lịch sử thanh toán vào database
                    PaymentHistory paymentHistory = new PaymentHistory();
                    paymentHistory.setUser(user);
                    paymentHistory.setTransactionId(params.get("vnp_TransactionNo"));
                    paymentHistory.setOrderId(classroomId);
                    paymentHistory.setAmount(Long.valueOf(params.get("vnp_Amount")) / 100); // Số tiền đã được nhân với 100
                    paymentHistory.setStatus("Success");
                    paymentHistory.setPaymentDate(new java.util.Date());
                    paymentHistory.setBankCode(params.get("vnp_BankCode"));
                    paymentHistory.setCardType(params.get("vnp_CardType"));
                    paymentHistory.setOrderInfo(orderInfo);  // Lưu orderInfo có classroomId và timestamp

                    paymentHistoryService.save(paymentHistory);

                    // Chuyển hướng về client với trạng thái thành công
                    response.sendRedirect("http://localhost:3000/payment/success?orderId=" + orderId + "&classroomId=" + classroomId);
                    return;
                }
            }

            // Chuyển hướng về client với trạng thái thất bại nếu thanh toán không thành công
            response.sendRedirect("http://localhost:3000/payment/failure");
        } else {
            // Chuyển hướng về client với trạng thái không hợp lệ nếu VNPay không xác nhận thanh toán hợp lệ
            response.sendRedirect("http://localhost:3000/payment/invalid");
        }
    }


}
