package com.ptit.EnglishExplorer.data.repository;

import com.ptit.EnglishExplorer.data.entity.Exam;
import com.ptit.EnglishExplorer.data.entity.UserExam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserExamRepository extends JpaRepository<UserExam, Long> {
}
