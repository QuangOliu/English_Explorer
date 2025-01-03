package com.ptit.EnglishExplorer.data.repository;

import com.ptit.EnglishExplorer.data.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProgressRepository extends JpaRepository<Progress, Long> {

    @Query("SELECT p FROM Progress p WHERE p.user.id = :userId AND p.lesson.id = :lessonId")
    Optional<Progress> findByUserAndLesson(Long userId, Long lessonId);

    @Query("SELECT COUNT(p) FROM Progress p " +
            "JOIN p.lesson l " +
            "JOIN l.chapter c " +
            "JOIN c.course course " +
            "WHERE course.id = :courseId AND p.user.id = :userId AND p.status = 1")
    long countCompletedLessonsByUserAndCourse(@Param("userId") Long userId, @Param("courseId") Long courseId);

}
