package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.data.entity.ClassMember;
import com.ptit.EnglishExplorer.data.entity.Exam;
import com.ptit.EnglishExplorer.data.service.ClassMemberService;
import com.ptit.EnglishExplorer.data.service.ClassroomService;
import com.ptit.EnglishExplorer.data.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/classmembers")
public class ClassMemberController extends BaseController<ClassMember, Long, ClassMemberService> {
    @Autowired
    public ClassMemberController(ClassMemberService service) {
        super(service);
    }
}
