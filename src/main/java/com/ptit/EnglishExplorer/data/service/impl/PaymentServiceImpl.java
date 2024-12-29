package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.config.Config;
import com.ptit.EnglishExplorer.config.VNPAYConfig;
import com.ptit.EnglishExplorer.data.dto.PaymentResDTO;
import com.ptit.EnglishExplorer.data.service.PaymentService;
import com.ptit.EnglishExplorer.utils.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final VNPAYConfig vnPayConfig;

    public PaymentResDTO createVnPayPayment(HttpServletRequest request) {
        try {

//            String decryptedAmount = decryptAmount(request.getParameter("amount"), "toilamotchillguysieucapcutedethuongsieucap");
            long amount = Integer.parseInt("100000") * 100L;
//            long amount = Integer.parseInt(decryptedAmount) * 100L;
            String bankCode = request.getParameter("bankCode");
//            String orderId = request.getParameter("orderId");
//            String bankCode = "NCB";
            String orderId = LocalDate.now().toString();
            // Kiểm tra nếu mã đơn hàng không có trong request thì trả về lỗi
//            if (orderId == null || orderId.isEmpty()) {
//                return new PaymentResDTO("error", "Erro", "Error");
//            }
            Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
            vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
            vnpParamsMap.put("vnp_TxnRef", orderId);  // Thêm mã đơn hàng vào tham số vnp_TxnRef
            vnpParamsMap.put("vnp_BankCode", bankCode);
            vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
            //build query url
            String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
            String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
            String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
            queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
            String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;

            return new PaymentResDTO("ok", "Success", paymentUrl);
        } catch (Exception e) {
            return new PaymentResDTO("error", "Sai mã", "");
        }


    }
    // Hàm giải mã
    public String decryptAmount(String encryptedAmount, String secretKey) throws Exception {
        // Chuyển secret key thành dạng byte (16 bytes - AES yêu cầu)
        byte[] keyBytes = secretKey.getBytes("UTF-8");
        SecretKey key = new SecretKeySpec(keyBytes, 0, 16, "AES");

        // Khởi tạo cipher với AES
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);

        // Giải mã chuỗi mã hóa
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedAmount);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);

        // Chuyển về chuỗi ban đầu
        return new String(decryptedBytes, "UTF-8");
    }
}
