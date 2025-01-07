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
        if (this == obj) {
            return true; // Nếu cùng tham chiếu, trả về true.
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // Nếu đối tượng null hoặc khác lớp, trả về false.
        }

        Choise choise = (Choise) obj;

        // So sánh tất cả các trường
        return Objects.equals(this.id, choise.id) &&
                Objects.equals(this.question, choise.question) &&
                Objects.equals(this.answer, choise.answer) &&
                this.correct == choise.correct;
    }


    @Override
    public int hashCode() {
        return Objects.hash(answer);
    }
}
