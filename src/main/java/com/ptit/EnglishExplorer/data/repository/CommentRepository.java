package com.ptit.EnglishExplorer.data.repository;

import com.ptit.EnglishExplorer.data.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("Select c from Comment c where c.question.id = :id")
    List<Comment> getCommentByQuestionId(Long id);
}
