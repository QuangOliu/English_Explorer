package com.ptit.EnglishExplorer.data.service;

import com.ptit.EnglishExplorer.data.entity.Exam;
import com.ptit.EnglishExplorer.data.entity.UserExam;

import java.util.List;

public interface UserExamService extends CrudService<UserExam, Long> {
    List<UserExam> getAllUserExamInExams(Long id);
}
