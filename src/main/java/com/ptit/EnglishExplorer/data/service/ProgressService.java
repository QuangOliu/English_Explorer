package com.ptit.EnglishExplorer.data.service;

import com.ptit.EnglishExplorer.data.entity.Lesson;
import com.ptit.EnglishExplorer.data.entity.Progress;

public interface ProgressService extends CrudService<Progress, Long> {
    public Progress markAsCompleted(Lesson lesson);
}
