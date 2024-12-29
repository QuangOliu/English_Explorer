package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.data.entity.Choise;
import com.ptit.EnglishExplorer.data.entity.PaymentHistory;
import com.ptit.EnglishExplorer.data.repository.ChoiseRepository;
import com.ptit.EnglishExplorer.data.repository.PaymentHistoryRepository;
import com.ptit.EnglishExplorer.data.service.ChoiseService;
import com.ptit.EnglishExplorer.data.service.PaymentHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentHistoryServiceImpl extends BaseServiceImpl<PaymentHistory, Long, PaymentHistoryRepository> implements PaymentHistoryService {

    @Autowired
    public PaymentHistoryServiceImpl(PaymentHistoryRepository repository) {
        super(repository);
    }

}
