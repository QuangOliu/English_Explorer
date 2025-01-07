package com.ptit.EnglishExplorer.data.repository;

import com.ptit.EnglishExplorer.data.entity.Classroom;
import com.ptit.EnglishExplorer.data.entity.Exam;
import com.ptit.EnglishExplorer.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    // Sử dụng @Query để viết câu truy vấn HQL, tìm lớp học của giáo viên dựa trên username
    @Query("SELECT c FROM Classroom c WHERE c.createdBy = :username")
    Page<Classroom> findByTeacher(@Param("username") String username, Pageable pageable);
}
