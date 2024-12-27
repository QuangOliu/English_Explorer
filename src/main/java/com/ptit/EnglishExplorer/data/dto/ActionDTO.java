package com.ptit.EnglishExplorer.data.dto;

import com.ptit.EnglishExplorer.data.entity.Action;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionDTO {
    private Long id;
    private Long userId;
    private Long questionId;
    private String actionType;

    // Constructor từ Action entity
    public ActionDTO(Action action) {
        this.id = action.getId();
        this.userId = action.getUser().getId();
        this.questionId = action.getQuestion().getId();
        this.actionType = action.getActionType().name();
    }
}
