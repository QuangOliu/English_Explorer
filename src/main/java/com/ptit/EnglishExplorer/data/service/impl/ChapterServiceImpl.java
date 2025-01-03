package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.data.entity.Chapter;
import com.ptit.EnglishExplorer.data.repository.ChapterRepository;
import com.ptit.EnglishExplorer.data.service.ChapterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterServiceImpl extends BaseServiceImpl<Chapter, Long, ChapterRepository> implements ChapterService {

    public ChapterServiceImpl(ChapterRepository repository) {
        super(repository);
    }

    @Override
    public List<Chapter> getByCourse(Long id) {
        return repository.getByCourse(id);
    }
}
