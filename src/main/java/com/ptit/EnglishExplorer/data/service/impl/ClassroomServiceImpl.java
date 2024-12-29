package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.auditing.ApplicationAuditAware;
import com.ptit.EnglishExplorer.data.entity.Classroom;
import com.ptit.EnglishExplorer.data.entity.User;
import com.ptit.EnglishExplorer.data.repository.ClassroomRepository;
import com.ptit.EnglishExplorer.data.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassroomServiceImpl extends BaseServiceImpl<Classroom, Long, ClassroomRepository> implements ClassroomService {

    @Autowired
    public ClassroomServiceImpl(ClassroomRepository repository) {
        super(repository);
    }


    @Override
    public Classroom save(Classroom entity) {
        Classroom newEntity = null;
        if(entity.getId() != null) {
            newEntity = repository.findById(entity.getId()).get();
        }
        if(newEntity == null) {
            newEntity = new Classroom();
            User user = ApplicationAuditAware.getCurrentUser();
            if(user != null) {
                newEntity.setUser(user);
            }
        }
        newEntity.setName(entity.getName());
        newEntity.setDescription(entity.getDescription());
        newEntity.setAccessType(entity.getAccessType());
        newEntity.setCost(entity.getCost());

        return repository.save(entity);
    }


}
