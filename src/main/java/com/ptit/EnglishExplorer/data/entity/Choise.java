package com.ptit.EnglishExplorer.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ptit.EnglishExplorer.data.entity.auditing.AuditableEntity;
import com.ptit.EnglishExplorer.data.types.LevelType;
import com.ptit.EnglishExplorer.data.types.SkillType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Data
@Table(name = "tbl_choise")
public class Choise extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;


    // Trong lớp Choise
    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonBackReference
    private Question question;

    private String answer;

    private boolean correct;

    @Override
    public String toString() {
        return "Choise{" +
                "correct=" + correct +
                ", answer='" + answer + '\'' +
                ", id=" + id +
                '}';
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Choise choise = (Choise) obj;

        return this.answer != null && this.answer.equals(choise.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(answer);
    }
}
