package com.ptit.EnglishExplorer.data.entity;


import com.ptit.EnglishExplorer.data.entity.auditing.AuditableEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tbl_notification")
public class Notification extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    User user;

    private String title;

    private String text;

    @Column(name = "is_read", nullable = false)
    private boolean read; // Changed from 'readed' to 'read'

}
