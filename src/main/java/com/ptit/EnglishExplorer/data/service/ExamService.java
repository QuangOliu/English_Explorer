package com.ptit.EnglishExplorer.data.service;

import com.ptit.EnglishExplorer.data.entity.Action;
import com.ptit.EnglishExplorer.data.entity.Exam;

import java.util.List;

public interface ExamService extends CrudService<Exam, Long> {
    public List<Exam> getExamsByClassroomId(Long classroomId);
}
