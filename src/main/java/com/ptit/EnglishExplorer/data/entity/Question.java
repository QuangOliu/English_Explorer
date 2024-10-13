package com.ptit.EnglishExplorer.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ptit.EnglishExplorer.data.entity.auditing.AuditableEntity;
import com.ptit.EnglishExplorer.data.types.LevelType;
import com.ptit.EnglishExplorer.data.types.SkillType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@Table(name = "tbl_question")
public class Question extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = true)  // Changed from "lession_id" to "lesson_id"
    @JsonBackReference
    private Lesson lesson;

    // Trong lớp Question
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    Set<Choise> choises = new HashSet<>();

    // Quan hệ nhiều-nhiều với Exam
    @ManyToMany(mappedBy = "questions", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Exam> exams = new HashSet<>();

    private String question;

    private String image;

    private String audio;

    private SkillType skill;

    private LevelType level;

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", lesson=" + lesson +
                ", choises=" + choises +
                ", question='" + question + '\'' +
                ", image='" + image + '\'' +
                ", audio='" + audio + '\'' +
                ", skill=" + skill +
                ", level=" + level +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // chỉ dựa vào ID
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Question)) return false;
        Question question = (Question) obj;
        return Objects.equals(id, question.id);
    }
}
