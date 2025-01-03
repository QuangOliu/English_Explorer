package com.ptit.EnglishExplorer.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ptit.EnglishExplorer.data.entity.auditing.AuditableEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_chapter")
@Data
public class Chapter extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course; // Một chương thuộc về một mon hoc

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "order_index", nullable = true) // Thêm trường orderIndex để xác định thứ tự của chương
    private int orderIndex;

    @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // Use JsonManagedReference to serialize chapters' lessons correctly
    private Set<Lesson> lessons = new HashSet<>(); // Một chương có thể có nhiều bài học

}
