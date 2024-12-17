package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.auditing.ApplicationAuditAware;
import com.ptit.EnglishExplorer.data.entity.ClassMember;
import com.ptit.EnglishExplorer.data.entity.Exam;
import com.ptit.EnglishExplorer.data.entity.User;
import com.ptit.EnglishExplorer.data.service.ClassMemberService;
import com.ptit.EnglishExplorer.data.service.ClassroomService;
import com.ptit.EnglishExplorer.data.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/classmembers")
public class ClassMemberController extends BaseController<ClassMember, Long, ClassMemberService> {
    @Autowired
    public ClassMemberController(ClassMemberService service) {
        super(service);
    }

    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<List<ClassMember>> getByClassroomId(@PathVariable Long classroomId) {
        List<ClassMember> exams = service.getByClassroomId(classroomId);
        if (exams.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(exams);
    }

    @DeleteMapping(path = "/kick/{userId}/{classroomId}")
    public ResponseEntity<String> kick(@PathVariable Long userId, @PathVariable Long classroomId) {
        boolean result = service.kickUser(userId, classroomId);
        if (result) {
            return ResponseEntity.ok("Successfully kicked the user");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to kick the user");
        }
    }


}
