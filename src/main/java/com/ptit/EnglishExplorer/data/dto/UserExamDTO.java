package com.ptit.EnglishExplorer.data.dto;

import com.ptit.EnglishExplorer.data.entity.UserExam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserExamDTO {
    private Long id;
    private UserDTO user;
    private ExamDTO exam;
    private Double score;

    public UserExamDTO(UserExam userExam) {
        this.id = userExam.getId();
        this.user = new UserDTO(userExam.getUser());
        this.exam = new ExamDTO(userExam.getExam());
        this.score = userExam.getScore();
    }
}
