package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.data.entity.Exam;
import com.ptit.EnglishExplorer.data.entity.UserExam;
import com.ptit.EnglishExplorer.data.repository.ExamRepository;
import com.ptit.EnglishExplorer.data.repository.UserExamRepository;
import com.ptit.EnglishExplorer.data.service.ExamService;
import com.ptit.EnglishExplorer.data.service.UserExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserExamServiceImpl extends BaseServiceImpl<UserExam, Long, UserExamRepository> implements UserExamService {

    @Autowired
    public UserExamServiceImpl(UserExamRepository repository) {
        super(repository);
    }

}
