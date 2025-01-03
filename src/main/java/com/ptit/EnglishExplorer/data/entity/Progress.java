package com.ptit.EnglishExplorer.data.entity;

import com.ptit.EnglishExplorer.data.entity.auditing.AuditableEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tbl_progress", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "lesson_id"})
})
@Data
public class Progress extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    Lesson lesson;

    Integer status = 0; //1 da hoc xong,
}
