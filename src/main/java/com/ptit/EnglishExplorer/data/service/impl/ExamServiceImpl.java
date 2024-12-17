package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.auditing.ApplicationAuditAware;
import com.ptit.EnglishExplorer.data.dto.AnswerDto;
import com.ptit.EnglishExplorer.data.entity.*;
import com.ptit.EnglishExplorer.data.repository.*;
import com.ptit.EnglishExplorer.data.service.ExamService;
import com.ptit.EnglishExplorer.data.service.UserExamService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ExamServiceImpl extends BaseServiceImpl<Exam, Long, ExamRepository> implements ExamService {
    @Autowired
    private ClassroomRepository classroomRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserExamService userExamService;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ChoiseRepository choiseRepository;

    @Autowired
    public ExamServiceImpl(ExamRepository repository) {
        super(repository);
    }

    @Override
    public List<Exam> getExamsByClassroomId(Long classroomId) {
        return repository.findByClassroomId(classroomId);
    }

    @Override
    public ResponseEntity<UserExam> doExam(Long examId, List<AnswerDto> answers) {
        if (examId == null || answers == null || answers.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        Exam exam = entityManager.find(Exam.class, examId);
        if (exam == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        int numberQuestionOfExam = exam.getQuestions().size();

        if(numberQuestionOfExam == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        UserExam entity = new UserExam();
        User user = ApplicationAuditAware.getCurrentUser();
        entity.setUser(user);
        entity.setExam(exam);

        double score = 0;
        int count = 0;
        for (AnswerDto answer : answers) {
            Optional<Question> questionOpt = questionRepository.findById(answer.getQuestion().getId());
            if(answer.getAnswerId() == null) {
                continue;
            }
            Optional<Choise> choiseOpt = choiseRepository.findById(answer.getAnswerId());

            if (questionOpt.isPresent() && choiseOpt.isPresent()) {
                Question question = questionOpt.get();
                Choise selectedChoice = choiseOpt.get();

                if (selectedChoice.getQuestion().getId().equals(question.getId())) {
                    if (selectedChoice.isCorrect()) {
                        count += 1;
                    }
                }
            }
        }

        score = ((double) count /numberQuestionOfExam)*10;

        // Tính điểm cuối cùng dựa trên số câu đúng và hệ số
        score = ((double) count / numberQuestionOfExam) * 10;

        // Làm tròn điểm nếu cần (2 chữ số thập phân chẳng hạn)
        score = Math.round(score * 100.0) / 100.0;

        entity.setScore(score);
        UserExam savedEntity = userExamService.save(entity);
        return ResponseEntity.ok(savedEntity);
    }


    @Transactional
    @Override
    public Exam save(Exam entity) {
        // Handle new or existing exam
        Exam exam;
        if (entity.getId() != null) {
            // Update existing exam
            exam = repository.findById(entity.getId())
                    .orElseThrow(() -> new RuntimeException("Exam not found"));

            // Clear existing questions if updating
            exam.getQuestions().clear();
        } else {
            // Creating a new exam
            exam = new Exam();
        }

        // Set the exam properties
        exam.setTitle(entity.getTitle());
        exam.setDescription(entity.getDescription());
        exam.setAccessType(entity.getAccessType());
        exam.setType(entity.getType());
        exam.setStatus(entity.getStatus());
        exam.setStartDate(entity.getStartDate());
        exam.setEndDate(entity.getEndDate());

        // Check if the classroom is provided; if not, retain the existing one
        if (entity.getClassroom() != null && entity.getClassroom().getId() != null) {
            Classroom classroom = classroomRepository.findById(entity.getClassroom().getId())
                    .orElseThrow(() -> new RuntimeException("Classroom not found"));
            exam.setClassroom(classroom);
        } else {
            // If no classroom is provided, retain the existing classroom
            if (exam.getClassroom() == null) {
                throw new IllegalArgumentException("Classroom must not be null");
            }
        }


        // Add or merge questions from the entity
        for (Question question : entity.getQuestions()) {
            if (question.getId() != null) {
                // Merge existing question
                question = entityManager.merge(question);
            }
            exam.getQuestions().add(question); // Add the question to the exam
        }

        // Save or update the exam
        return repository.save(exam);
    }

}
