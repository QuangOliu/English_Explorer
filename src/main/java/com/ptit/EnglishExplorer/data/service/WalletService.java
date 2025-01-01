package com.ptit.EnglishExplorer.data.service;

import com.ptit.EnglishExplorer.data.entity.User;
import com.ptit.EnglishExplorer.data.entity.Wallet;

public interface WalletService extends CrudService<Wallet, Long> {
    Wallet findByUser(Long userId);
}
