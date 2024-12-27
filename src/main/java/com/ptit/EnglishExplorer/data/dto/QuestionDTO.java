package com.ptit.EnglishExplorer.data.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ptit.EnglishExplorer.data.entity.Choise;
import com.ptit.EnglishExplorer.data.entity.Exam;
import com.ptit.EnglishExplorer.data.entity.Lesson;
import com.ptit.EnglishExplorer.data.entity.Question;
import com.ptit.EnglishExplorer.data.entity.auditing.AuditableEntity;
import com.ptit.EnglishExplorer.data.types.LevelType;
import com.ptit.EnglishExplorer.data.types.SkillType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO extends AuditableEntity {
    private Long id;
    Set<ChoiseDTO> choises = new HashSet<>();
    private String question;
    private String explanation;
    private String image;
    private String audio;
    private SkillType skill;
    private LevelType level;

    public QuestionDTO(Question question) {
        this.id = question.getId();
        this.question = question.getQuestion();
        this.explanation = question.getExplanation();
        this.image = question.getImage();
        this.audio = question.getAudio();
        this.level = question.getLevel();
        this.skill = question.getSkill();

        if(question.getChoises() != null) {
            for (Choise choise : question.getChoises()) {
                this.choises.add(new ChoiseDTO(choise));
            }
        }
    }
}
