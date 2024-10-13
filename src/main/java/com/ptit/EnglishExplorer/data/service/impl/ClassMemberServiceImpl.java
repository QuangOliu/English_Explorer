package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.data.entity.ClassMember;
import com.ptit.EnglishExplorer.data.repository.ClassMemberRepository;
import com.ptit.EnglishExplorer.data.repository.ClassroomRepository;
import com.ptit.EnglishExplorer.data.service.ClassMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassMemberServiceImpl extends BaseServiceImpl<ClassMember, Long, ClassMemberRepository> implements ClassMemberService {

    @Autowired
    public ClassMemberServiceImpl(ClassMemberRepository repository) {
        super(repository);
    }

}
