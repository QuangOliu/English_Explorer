package com.ptit.EnglishExplorer.data.repository;

import com.ptit.EnglishExplorer.data.entity.Exam;
import com.ptit.EnglishExplorer.data.entity.UserExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserExamRepository extends JpaRepository<UserExam, Long> {

    @Query("SELECT ue FROM UserExam ue WHERE ue.exam.id =?1")
    List<UserExam> getByExam(long id);
}
