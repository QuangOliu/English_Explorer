package com.ptit.EnglishExplorer.data.dto;

import com.ptit.EnglishExplorer.data.types.LevelType;
import com.ptit.EnglishExplorer.data.types.SkillType;
import lombok.Data;

@Data
public class QuestionSearchDto {
    SkillType skillType;
    LevelType levelType;
    String question;
}