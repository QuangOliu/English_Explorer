package com.ptit.EnglishExplorer.data.service;

import com.ptit.EnglishExplorer.data.entity.Chapter;

import java.util.List;

public interface ChapterService extends CrudService<Chapter, Long> {
    List<Chapter> getByCourse(Long id);
}
