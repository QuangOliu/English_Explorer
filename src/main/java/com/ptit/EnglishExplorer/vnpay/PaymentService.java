package com.ptit.EnglishExplorer.vnpay;

import com.ptit.EnglishExplorer.config.VNPAYConfig;
import com.ptit.EnglishExplorer.utils.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final VNPAYConfig vnPayConfig;
    public PaymentDTO.VNPayResponse createVnPayPayment(long price, HttpServletRequest request, String orderInfo) {
        long amount = price * 100L;  // Tính số tiền thanh toán (nhân với 100 vì VNPay yêu cầu số tiền tính bằng đơn vị xu)

        String bankCode = "NCB";  // Mã ngân hàng (ví dụ ở đây là NCB)

        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));  // Thêm số tiền vào tham số
        vnpParamsMap.put("vnp_OrderInfo", orderInfo);  // Thêm thông tin đơn hàng (có timestamp)

        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);  // Thêm mã ngân hàng nếu có
        }

        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));  // Lấy địa chỉ IP của người dùng

        // Tạo URL thanh toán
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);  // Tạo URL truy vấn
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);  // Tạo hash từ URL truy vấn
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);  // Tính toán vnp_SecureHash

        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;  // Thêm vnp_SecureHash vào URL
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;  // URL thanh toán cuối cùng

        // Trả về đối tượng PaymentDTO.VNPayResponse
        return PaymentDTO.VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl)
                .build();
    }

}
