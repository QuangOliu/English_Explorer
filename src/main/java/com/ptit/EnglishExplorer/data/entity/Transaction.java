package com.ptit.EnglishExplorer.data.entity;

import com.ptit.EnglishExplorer.data.entity.auditing.AuditableEntity;
import com.ptit.EnglishExplorer.data.types.TransactionStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.domain.Auditable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tbl_transaction")
public class Transaction extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private Long amount;

    private Long createdAt;  // Thêm trường createdAt để lưu thời gian tạo

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private TransactionStatus status = TransactionStatus.PENDING;

    @Column(name = "ref", nullable = false)
    private String ref;

    @PrePersist
    public void prePersist() {
        this.createdAt = System.currentTimeMillis();  // Gán thời gian tạo khi giao dịch được lưu vào DB
    }
}