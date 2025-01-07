package com.ptit.EnglishExplorer.data.repository;

import com.ptit.EnglishExplorer.data.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("SELECT COUNT(l) FROM Lesson l " +
            "JOIN l.chapter c " +
            "JOIN c.course course " +
            "WHERE course.id = :courseId")
    Long findByCourseId(Long courseId);
}
