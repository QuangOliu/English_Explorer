package com.ptit.EnglishExplorer.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ptit.EnglishExplorer.data.entity.auditing.AuditableEntity;
import com.ptit.EnglishExplorer.data.types.LevelType;
import com.ptit.EnglishExplorer.data.types.SkillType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.*;

@Entity
@Data
@Table(name = "tbl_question")
public class Question extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "lesson_id", nullable = true)
    private Lesson lesson;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "exam_id")  // Changed from "lession_id" to "lesson_id"
    private Exam exam;

    // Trong lớp Question
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    List<Choise> choises = new ArrayList<>();


    private String question;

    private String explanation;

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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Question)) return false;
        Question other = (Question) obj;
        return Objects.equals(id, other.id) &&
                Objects.equals(question, other.question) &&
                Objects.equals(explanation, other.explanation) &&
                Objects.equals(image, other.image) &&
                Objects.equals(audio, other.audio) &&
                skill == other.skill &&
                level == other.level;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lesson, exam, choises, question, explanation, image, audio, skill, level);
    }

}
