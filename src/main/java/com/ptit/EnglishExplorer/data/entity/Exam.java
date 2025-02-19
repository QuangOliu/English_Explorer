package com.ptit.EnglishExplorer.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.ptit.EnglishExplorer.data.entity.auditing.AuditableEntity;
import com.ptit.EnglishExplorer.data.types.AccessTypeCustom;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Data
@Table(name = "tbl_exam")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Exam extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    private String title;

    private AccessTypeCustom accessType = AccessTypeCustom.PRIVATE; // Khởi tạo mặc định

    private String description;

    private String type;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
     Set<Question> questions = new HashSet<>();

    @NotNull
    @ManyToOne
    @JoinColumn(name = "classroom_id")
    @JsonBackReference
    Classroom classroom;

    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // chỉ dựa vào ID
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // So sánh địa chỉ đối tượng
        if (!(obj instanceof Choise)) return false; // Kiểm tra kiểu đối tượng
        Exam exam = (Exam) obj; // Ép kiểu đối tượng

        // So sánh trường answer
        return Objects.equals(id, exam.id);
    }
}
