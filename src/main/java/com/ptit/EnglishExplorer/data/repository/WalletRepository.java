package com.ptit.EnglishExplorer.data.repository;

import com.ptit.EnglishExplorer.data.entity.User;
import com.ptit.EnglishExplorer.data.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    // Tìm ví theo userId
    Wallet findByUser_Id(Long userId);

    // Kiểm tra ví có tồn tại theo userId
    boolean existsByUser_Id(Long userId);

    // Truy vấn tùy chỉnh: tìm ví của người dùng theo userId
    @Query("SELECT w FROM Wallet w WHERE w.user.id = :userId")
    Wallet findWalletByUserId(Long userId);
}
