package com.ptit.EnglishExplorer.data.service;

import com.ptit.EnglishExplorer.data.entity.Transaction;
import com.ptit.EnglishExplorer.vnpay.PaymentDTO;
import com.ptit.EnglishExplorer.vnpay.ResponseObject;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface TransactionService extends CrudService<Transaction, Long> {
    List<Transaction> findByUserId(Long id);

    PaymentDTO.VNPayResponse payment(Long amount, HttpServletRequest request);

    String callback(Map<String, String> params);
}
