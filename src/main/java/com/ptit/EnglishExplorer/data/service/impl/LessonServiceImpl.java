package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.data.entity.History;
import com.ptit.EnglishExplorer.data.entity.Lesson;
import com.ptit.EnglishExplorer.data.repository.HistoryRepository;
import com.ptit.EnglishExplorer.data.repository.LessonRepository;
import com.ptit.EnglishExplorer.data.service.HistoryService;
import com.ptit.EnglishExplorer.data.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonServiceImpl extends BaseServiceImpl<Lesson, Long, LessonRepository> implements LessonService {

    @Autowired
    public LessonServiceImpl(LessonRepository repository) {
        super(repository);
    }

}
