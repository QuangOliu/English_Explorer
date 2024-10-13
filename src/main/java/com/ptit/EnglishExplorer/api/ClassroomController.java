package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.data.entity.Action;
import com.ptit.EnglishExplorer.data.entity.Classroom;
import com.ptit.EnglishExplorer.data.service.ActionService;
import com.ptit.EnglishExplorer.data.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/classrooms")
public class ClassroomController extends BaseController<Classroom, Long, ClassroomService> {
    @Autowired
    public ClassroomController(ClassroomService service) {
        super(service);
    }
}
