package com.ptit.EnglishExplorer.data.repository;

import com.ptit.EnglishExplorer.data.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT c FROM Course c WHERE c.classroom.id = :id")
    List<Course> getByClassroom(Long id);

}
