package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.data.dto.QuestionSearchDto;
import com.ptit.EnglishExplorer.data.entity.Notification;
import com.ptit.EnglishExplorer.data.entity.Question;
import com.ptit.EnglishExplorer.data.service.NotificationService;
import com.ptit.EnglishExplorer.data.service.QuestionService;
import com.ptit.EnglishExplorer.data.types.SkillType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/questions")
public class QuestionController extends BaseController<Question, Long, QuestionService> {

    @Autowired
    public QuestionController(QuestionService service) {
        super(service);
    }

    @GetMapping(path="/get-by-exam/{examId}")
    public List<Question> getByExam(@PathVariable Long examId) {
        return service.getByExam(examId);
    }


    @PostMapping(path = "/search")
    public List<Question> search(@RequestBody QuestionSearchDto filter) {
        return service.searchQuestions(filter);
    }

    @PostMapping(value = "/upload/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> uploadImage(
            @RequestPart(value = "imageFile") MultipartFile imageFile) {
        try {
            // Gọi phương thức lưu hình ảnh
            String imageUrl = service.saveImage(imageFile);
            return ResponseEntity.status(HttpStatus.CREATED).body(imageUrl);
        } catch (Exception e) {
            System.err.println("Error while uploading image: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/upload/audio", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> uploadAudio(
            @RequestPart(value = "audioFile") MultipartFile audioFile) {
        try {
            // Gọi phương thức lưu âm thanh
            String audioUrl = service.saveAudio(audioFile);
            return ResponseEntity.status(HttpStatus.CREATED).body(audioUrl);
        } catch (Exception e) {
            System.err.println("Error while uploading audio: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
