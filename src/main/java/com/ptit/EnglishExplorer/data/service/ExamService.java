package com.ptit.EnglishExplorer.data.service;

import com.ptit.EnglishExplorer.data.dto.AnswerDto;
import com.ptit.EnglishExplorer.data.entity.Action;
import com.ptit.EnglishExplorer.data.entity.Exam;
import com.ptit.EnglishExplorer.data.entity.UserExam;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExamService extends CrudService<Exam, Long> {
    public List<Exam> getExamsByClassroomId(Long classroomId);

    ResponseEntity<UserExam> doExam(Long examId, List<AnswerDto> answers);
}
