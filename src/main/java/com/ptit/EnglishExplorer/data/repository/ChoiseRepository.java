package com.ptit.EnglishExplorer.data.repository;

import com.ptit.EnglishExplorer.data.entity.Choise;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChoiseRepository extends JpaRepository<Choise, Long> {

    // Phương thức xóa tất cả các lựa chọn theo questionId
    @Modifying
    @Transactional
    @Query("DELETE FROM Choise c WHERE c.question.id = :questionId")
    void deleteByQuestionId(@Param("questionId") Long questionId);
}
