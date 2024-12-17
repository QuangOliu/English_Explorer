package com.ptit.EnglishExplorer.data.dto;

import com.ptit.EnglishExplorer.data.entity.Choise;
import com.ptit.EnglishExplorer.data.entity.Question;
import com.ptit.EnglishExplorer.data.entity.auditing.AuditableEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {
    private Question question; // Trùng với "question" trong JSON
    private Long answerId;     // Trùng với "answerId" trong JSON
}
