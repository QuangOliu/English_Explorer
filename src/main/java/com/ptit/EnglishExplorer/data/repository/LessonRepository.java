package com.ptit.EnglishExplorer.data.repository;

import com.ptit.EnglishExplorer.data.entity.History;
import com.ptit.EnglishExplorer.data.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
