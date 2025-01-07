package com.ptit.EnglishExplorer.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ptit.EnglishExplorer.data.entity.auditing.AuditableEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tbl_lesson")
@Data
public class Lesson extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Added auto-generation for id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chapter_id", nullable = false) // Changed course_id to chapter_id
    @JsonBackReference // Use JsonBackReference to avoid circular references when serializing to JSON
    private Chapter chapter; // Changed course to chapter

    @Column(name = "order_index", nullable = false) // Thêm trường orderIndex cho bài học
    private int orderIndex;

    private String title;

    @Lob // Chỉ thị trường này là kiểu LONGTEXT trong MySQL
    @Column(name = "content", columnDefinition = "LONGTEXT") // Cấu hình cột trong DB
    private String content;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Question> questions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lesson lesson = (Lesson) o;
        return Objects.equals(id, lesson.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
