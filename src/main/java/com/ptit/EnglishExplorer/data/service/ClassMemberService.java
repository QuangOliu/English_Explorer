package com.ptit.EnglishExplorer.data.service;

import com.ptit.EnglishExplorer.data.entity.ClassMember;
import com.ptit.EnglishExplorer.data.entity.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClassMemberService extends CrudService<ClassMember, Long> {
    List<ClassMember> getByClassroomId(Long classroomId);

    List<User> getAllUserByClassroom(Long id);

    boolean kickUser(Long userId, Long classroomId);
}
