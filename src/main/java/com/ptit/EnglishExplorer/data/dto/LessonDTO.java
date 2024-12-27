package com.ptit.EnglishExplorer.data.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ptit.EnglishExplorer.data.entity.Lesson;
import com.ptit.EnglishExplorer.data.entity.Question;
import com.ptit.EnglishExplorer.data.entity.auditing.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonDTO {
    private Long id;
    private UserDTO user;
    private Set<QuestionDTO> questions = new HashSet<>();

    public LessonDTO(Lesson lesson) {
        this.id = lesson.getId();
        this.user = new UserDTO(lesson.getUser());
        if (lesson.getQuestions() != null) {
            for (Question question : lesson.getQuestions()) {
                this.questions.add(new QuestionDTO());
            }
        }
    }
}
