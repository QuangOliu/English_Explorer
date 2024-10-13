package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.data.entity.Choise;
import com.ptit.EnglishExplorer.data.entity.Lesson;
import com.ptit.EnglishExplorer.data.entity.Notification;
import com.ptit.EnglishExplorer.data.entity.Question;
import com.ptit.EnglishExplorer.data.repository.ChoiseRepository;
import com.ptit.EnglishExplorer.data.repository.LessonRepository;
import com.ptit.EnglishExplorer.data.repository.NotificationRepository;
import com.ptit.EnglishExplorer.data.repository.QuestionRepository;
import com.ptit.EnglishExplorer.data.service.NotificationService;
import com.ptit.EnglishExplorer.data.service.QuestionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl extends BaseServiceImpl<Question, Long, QuestionRepository> implements QuestionService {

    @Autowired
    public QuestionServiceImpl(QuestionRepository repository) {
        super(repository);
    }

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private LessonRepository lessonRepository; // Nếu bạn cần tìm bài học

    @Autowired
    private ChoiseRepository choiseRepository;
    @Override
    @Transactional
    public Question save(Question entity) {
        if (entity.getLesson() != null) {
            Lesson lesson = lessonRepository.findById(entity.getLesson().getId())
                    .orElseThrow(() -> new RuntimeException("Lesson not found"));
            entity.setLesson(lesson);
        }

        Question question;
        if (entity.getId() == null) {
            // Tạo mới câu hỏi
            question = new Question();
        } else {
            // Cập nhật câu hỏi hiện có
            question = questionRepository.findById(entity.getId())
                    .orElseThrow(() -> new RuntimeException("Question not found"));
        }

        // Cập nhật thuộc tính câu hỏi
        question.setLesson(entity.getLesson());
        question.setImage(entity.getImage());
        question.setAudio(entity.getAudio());
        question.setSkill(entity.getSkill());
        question.setLevel(entity.getLevel());
        question.setQuestion(entity.getQuestion());

        // Xử lý các lựa chọn
        if (entity.getChoises() != null) {
            question.getChoises().clear(); // Xóa các lựa chọn cũ

            for (Choise choiseDTO : entity.getChoises()) {
                // Kiểm tra số lượng lựa chọn trước khi thêm
//                if (question.getChoises().size() < 4) {
                    Choise choise = new Choise();
                    choise.setAnswer(choiseDTO.getAnswer());
                    choise.setCorrect(choiseDTO.isCorrect());
                    choise.setQuestion(question); // Thiết lập câu hỏi cho lựa chọn
                    question.getChoises().add(choise); // Thêm lựa chọn vào câu hỏi
//                } else {
//                    throw new RuntimeException("A question can only have up to 4 choices.");
//                }
            }
        }

        return questionRepository.save(question); // Lưu câu hỏi
    }
}
