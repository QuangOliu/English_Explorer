package com.ptit.EnglishExplorer.data.entity;

import com.ptit.EnglishExplorer.data.entity.auditing.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentHistory extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Added auto-generation for id
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String transactionId; // Mã giao dịch

    @Column(nullable = false)
    private Long orderId; // ID đơn hàng (có thể là classroom ID hoặc Order ID)

    @Column(nullable = false)
    private Long amount; // Số tiền thanh toán

    @Column(nullable = false)
    private String status; // Trạng thái thanh toán (ví dụ: "Success", "Failed")

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate; // Thời gian thanh toán

    @Column(nullable = true)
    private String bankCode; // Mã ngân hàng (nếu có)

    @Column(nullable = true)
    private String cardType; // Loại thẻ (ATM, VISA, MasterCard...)

    @Column(nullable = true)
    private String orderInfo; // Thông tin đơn hàng
}
