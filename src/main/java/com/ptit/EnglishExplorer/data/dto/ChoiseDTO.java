package com.ptit.EnglishExplorer.data.dto;

import com.ptit.EnglishExplorer.data.entity.Choise;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChoiseDTO {
    private Long id;
    private Long questionId;
    private String answer;
    private boolean correct;

    // Constructor từ Choise entity
    public ChoiseDTO(Choise choise) {
        this.id = choise.getId();
        this.questionId = choise.getQuestion().getId();
        this.answer = choise.getAnswer();
        this.correct = choise.isCorrect();
    }
}
