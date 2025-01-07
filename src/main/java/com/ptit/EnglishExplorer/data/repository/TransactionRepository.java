package com.ptit.EnglishExplorer.data.repository;

import com.ptit.EnglishExplorer.data.entity.Transaction;
import com.ptit.EnglishExplorer.data.entity.Wallet;
import com.ptit.EnglishExplorer.data.types.TransactionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Tìm tất cả các giao dịch của một người dùng theo userId
    @Query("SELECT t FROM Transaction t WHERE t.user.id = :userId")
    List<Transaction> findByUserId(Long userId); // Thêm tham số userId vào truy vấn

    // Tìm giao dịch từ mã giao dịch (ref)
    @Query("SELECT t FROM Transaction t WHERE t.ref = :vnpOrderInfo")
    Transaction findByRef(String vnpOrderInfo); // Truy vấn theo ref để tìm giao dịch


    // Phương thức tìm giao dịch PENDING và đã tạo trước thời gian nhất định
    List<Transaction> findByStatusAndCreatedAtBefore(TransactionStatus status, long timestamp);

    @Query("SELECT t FROM Transaction t WHERE t.user.username = :username")
    Page<Transaction> findTransactionsByUser(String username, Pageable pageable);
}
