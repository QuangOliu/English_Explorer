package com.ptit.EnglishExplorer.data.service;

import com.ptit.EnglishExplorer.data.dto.PaymentResDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {
    public PaymentResDTO createVnPayPayment(HttpServletRequest request);
    public String decryptAmount(String encryptedAmount, String secretKey) throws Exception;

}
