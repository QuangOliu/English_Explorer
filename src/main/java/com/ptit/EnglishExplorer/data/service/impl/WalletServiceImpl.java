package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.data.entity.User;
import com.ptit.EnglishExplorer.data.entity.Wallet;
import com.ptit.EnglishExplorer.data.repository.WalletRepository;
import com.ptit.EnglishExplorer.data.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl extends BaseServiceImpl<Wallet, Long, WalletRepository> implements WalletService {

    @Autowired
    public WalletServiceImpl(WalletRepository repository) {
        super(repository);
    }

    @Override
    public Wallet findByUser(Long userId) {
        return repository.findWalletByUserId(userId);  // Giả sử có phương thức trong repository để tìm ví theo người dùng
    }
}
