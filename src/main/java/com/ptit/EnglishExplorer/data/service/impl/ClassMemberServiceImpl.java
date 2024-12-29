package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.auditing.ApplicationAuditAware;
import com.ptit.EnglishExplorer.data.entity.ClassMember;
import com.ptit.EnglishExplorer.data.entity.Classroom;
import com.ptit.EnglishExplorer.data.entity.User;
import com.ptit.EnglishExplorer.data.repository.ClassMemberRepository;
import com.ptit.EnglishExplorer.data.repository.ClassroomRepository;
import com.ptit.EnglishExplorer.data.repository.UserRepository;
import com.ptit.EnglishExplorer.data.service.ClassMemberService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassMemberServiceImpl extends BaseServiceImpl<ClassMember, Long, ClassMemberRepository> implements ClassMemberService {

    private final UserRepository userRepository;
    private final ClassroomRepository classroomRepository;

    @Autowired
    public ClassMemberServiceImpl(ClassMemberRepository repository, UserRepository userRepository, ClassroomRepository classroomRepository) {
        super(repository);
        this.userRepository = userRepository;
        this.classroomRepository = classroomRepository;
    }

    @Override
    public ClassMember save(ClassMember entity) {
        if (entity.getUser() == null) {
            User user = ApplicationAuditAware.getCurrentUser();
            entity.setUser(user);
        }
        if(entity.getUser()==null){
            return null;
        }
        return repository.save(entity);
    }

    @Override
    public List<ClassMember> getByClassroomId(Long classroomId) {
        return repository.findByClassroomId(classroomId);
    }

    @Override
    public List<User> getAllUserByClassroom(Long id) {
        return repository.getAllUserByClassroom(id);
    }

    @Override
    @Transactional
    public boolean kickUser(Long userId, Long classroomId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Classroom> classroom = classroomRepository.findById(classroomId);

        if (!user.isPresent() || !classroom.isPresent()) {
            return false;
        }

        try {
            // Attempt to delete the association between the user and classroom
            int rowsDeleted = repository.deleteByUserIdAndClassroomId(user.get().getId(), classroom.get().getId());

            // If rowsDeleted > 0, that means a record was deleted successfully
            return rowsDeleted > 0;
        } catch (Exception e) {
            // Log the exception for debugging/monitoring purposes
//            logger.error("Error occurred while kicking user from classroom: ", e);
            return false;
        }
    }

}
