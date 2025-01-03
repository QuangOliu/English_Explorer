package com.ptit.EnglishExplorer.data.repository;

import com.ptit.EnglishExplorer.data.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    @Query("SELECT c from Chapter c where c.course.id = :id order by c.orderIndex ASC")
    List<Chapter> getByCourse(Long id);

}
