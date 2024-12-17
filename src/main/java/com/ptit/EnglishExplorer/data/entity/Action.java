package com.ptit.EnglishExplorer.data.entity;

import com.ptit.EnglishExplorer.data.entity.auditing.AuditableEntity;
import com.ptit.EnglishExplorer.data.types.ActionType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;


@Entity
@Data
@Table(name = "tbl_action", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "question_id", "action_type"}))
public class Action extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    private ActionType actionType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return Objects.equals(id, action.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
