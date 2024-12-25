package com.ptit.EnglishExplorer.data.repository;

import com.ptit.EnglishExplorer.data.entity.Notification;
import com.ptit.EnglishExplorer.data.entity.Question;
import com.ptit.EnglishExplorer.data.types.ActionType;
import com.ptit.EnglishExplorer.data.types.SkillType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q WHERE q.image = :filename OR q.audio = :filename")
    List<Question> findByFileName(@Param("filename") String filename);

    List<Question> findBySkill(SkillType skill);

    @Query("SELECT q FROM Question q JOIN q.exams e WHERE e.id = :examId")
    List<Question> getByExam(@Param("examId") Long examId);

    @Query("SELECT a.question FROM Action a WHERE a.user.id = :userId AND a.actionType = :actionType ORDER BY a.question.createDate DESC")
    List<Question> findQuestionsByUserAndAction(@Param("userId") Long userId, @Param("actionType") ActionType actionType);

    @Query("SELECT a.question FROM Action a WHERE a.user.id = :userId ORDER BY a.question.createDate DESC")
    List<Question> findQuestionsByUser(@Param("userId") Long userId);
}

