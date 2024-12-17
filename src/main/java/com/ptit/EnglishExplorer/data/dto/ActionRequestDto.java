package com.ptit.EnglishExplorer.data.dto;

import com.ptit.EnglishExplorer.data.entity.Question;
import com.ptit.EnglishExplorer.data.types.ActionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionRequestDto {
    private Question question;
    private ActionType actionType;
}
