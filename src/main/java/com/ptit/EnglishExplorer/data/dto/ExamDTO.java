package com.ptit.EnglishExplorer.data.dto;

import com.ptit.EnglishExplorer.data.entity.Exam;
import com.ptit.EnglishExplorer.data.entity.Question;
import com.ptit.EnglishExplorer.data.entity.auditing.AuditableEntity;
import com.ptit.EnglishExplorer.data.types.AccessTypeCustom;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamDTO extends AuditableEntity {
    private Long id;
    private String title;
    private AccessTypeCustom accessType = AccessTypeCustom.PRIVATE; // Khởi tạo mặc định
    private String description;
    private String type;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    Set<QuestionDTO> questions = new HashSet<>();

    public ExamDTO(Exam exam) {
        this.id = exam.getId();
        this.title = exam.getTitle();
        this.accessType = exam.getAccessType();
        this.description = exam.getDescription();
        this.type = exam.getType();
        this.status = exam.getStatus();
        this.startDate = exam.getStartDate();
        this.endDate = exam.getEndDate();

        if(exam.getQuestions() != null) {
            for (Question question : exam.getQuestions()) {
                this.questions.add(new QuestionDTO(question));
            }
        }
    }

}
