package com.ptit.EnglishExplorer.data.repository;

import com.ptit.EnglishExplorer.data.entity.ClassMember;
import com.ptit.EnglishExplorer.data.entity.User;
import com.ptit.EnglishExplorer.data.entity.UserExam;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClassMemberRepository extends JpaRepository<ClassMember, Long> {

    @Query("SELECT cm FROM ClassMember cm WHERE cm.classroom.id = :classroomId")
    List<ClassMember> findByClassroomId(Long classroomId);

    @Query("SELECT cm.user FROM ClassMember cm WHERE cm.classroom.id = :id")
    List<User> getAllUserByClassroom(Long id);

    @Modifying
    @Query("DELETE FROM ClassMember cm WHERE cm.user.id = :userId AND cm.classroom.id = :classroomId")
    int deleteByUserIdAndClassroomId(@Param("userId") Long userId, @Param("classroomId") Long classroomId);

}
