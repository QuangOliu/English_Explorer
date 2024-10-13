package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.data.entity.Classroom;
import com.ptit.EnglishExplorer.data.entity.Exam;
import com.ptit.EnglishExplorer.data.service.ClassroomService;
import com.ptit.EnglishExplorer.data.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/exams")
public class ExamController extends BaseController<Exam, Long, ExamService> {
    @Autowired
    public ExamController(ExamService service) {
        super(service);
    }
}
