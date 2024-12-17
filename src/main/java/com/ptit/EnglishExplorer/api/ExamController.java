package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.auditing.ApplicationAuditAware;
import com.ptit.EnglishExplorer.data.dto.AnswerDto;
import com.ptit.EnglishExplorer.data.entity.Classroom;
import com.ptit.EnglishExplorer.data.entity.Exam;
import com.ptit.EnglishExplorer.data.entity.User;
import com.ptit.EnglishExplorer.data.entity.UserExam;
import com.ptit.EnglishExplorer.data.service.ClassroomService;
import com.ptit.EnglishExplorer.data.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/do-exam/{examId}")
    public ResponseEntity<UserExam> doExam(
            @PathVariable Long examId,
            @RequestBody List<AnswerDto> answers
    ) {
        // Log JSON nhận được
        System.out.println("Received JSON: " + answers);

        if (examId == null || answers == null || answers.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        // Phần xử lý logic...
        return  service.doExam(examId, answers);
    }
}
