package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.data.entity.Course;
import com.ptit.EnglishExplorer.data.entity.History;
import com.ptit.EnglishExplorer.data.repository.CourseRepository;
import com.ptit.EnglishExplorer.data.repository.HistoryRepository;
import com.ptit.EnglishExplorer.data.service.CourseService;
import com.ptit.EnglishExplorer.data.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryServiceImpl extends BaseServiceImpl<History, Long, HistoryRepository> implements HistoryService {

    @Autowired
    public HistoryServiceImpl(HistoryRepository repository) {
        super(repository);
    }

}
