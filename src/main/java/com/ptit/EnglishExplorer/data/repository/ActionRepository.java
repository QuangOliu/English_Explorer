package com.ptit.EnglishExplorer.data.repository;

import com.ptit.EnglishExplorer.data.entity.Action;
import com.ptit.EnglishExplorer.data.types.ActionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ActionRepository extends JpaRepository<Action, Long> {
    List<Action> findByUserIdAndQuestionId(Long userId, Long questionId);

    @Query("SELECT a FROM Action a WHERE a.user.id = :userId AND a.question.id = :questionId AND a.actionType = :actionType")
    Optional<Action> findByUserIdAndQuestionIdAndActionType(@Param("userId") Long userId,
                                                            @Param("questionId") Long questionId,
                                                            @Param("actionType") ActionType actionType);


}
