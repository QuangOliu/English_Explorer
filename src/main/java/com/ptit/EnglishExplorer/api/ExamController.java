package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.data.entity.Classroom;
import com.ptit.EnglishExplorer.data.entity.Exam;
import com.ptit.EnglishExplorer.data.service.ClassroomService;
import com.ptit.EnglishExplorer.data.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/exams")
public class ExamController extends BaseController<Exam, Long, ExamService> {
    @Autowired
    public ExamController(ExamService service) {
        super(service);
    }

    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<List<Exam>> getExamsByClassroomId(@PathVariable Long classroomId) {
        List<Exam> exams = service.getExamsByClassroomId(classroomId);
        if (exams.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(exams);
    }
}
