package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.auditing.ApplicationAuditAware;
import com.ptit.EnglishExplorer.data.dto.AnswerDto;
import com.ptit.EnglishExplorer.data.dto.ChoiseDTO;
import com.ptit.EnglishExplorer.data.dto.ExamDTO;
import com.ptit.EnglishExplorer.data.dto.QuestionDTO;
import com.ptit.EnglishExplorer.data.entity.*;
import com.ptit.EnglishExplorer.data.repository.ChoiseRepository;
import com.ptit.EnglishExplorer.data.repository.ClassroomRepository;
import com.ptit.EnglishExplorer.data.repository.ExamRepository;
import com.ptit.EnglishExplorer.data.repository.QuestionRepository;
import com.ptit.EnglishExplorer.data.service.ExamService;
import com.ptit.EnglishExplorer.data.service.UserExamService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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

        if (numberQuestionOfExam == 0) {
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
            if (answer.getAnswerId() == null) {
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


        for (Question question : entity.getQuestions()) {

            if (question.getId() != null) {
                // Merge existing question, no need to reassign to the variable
                entityManager.merge(question);
            }
            exam.getQuestions().add(question); // Add the question to the exam
        }

        // Save or update the exam
        return repository.save(exam);
    }

    @Override
    public Exam create(ExamDTO dto) {
        Exam entity = null;
        if (dto.getId() != null) {
            // Update existing exam
            entity = repository.findById(dto.getId())
                    .orElse(null);

        }

        if (entity == null) {
            entity = new Exam();
            // Check if the classroom is provided; if not, retain the existing one
            Classroom classroom = null;
            if (dto.getClassroom() != null && dto.getClassroom().getId() != null) {
                classroom = classroomRepository.findById(dto.getClassroom().getId())
                        .orElse(null);
            }
            entity.setClassroom(classroom);
        }

        // Set the exam properties
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setAccessType(dto.getAccessType());
        entity.setType(dto.getType());
        entity.setStatus(dto.getStatus());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());




        // Clear existing questions if updating
        if (entity.getQuestions() != null) {
            entity.getQuestions().clear();
        }else {
            entity.setQuestions(new HashSet<>());
        }
        for (QuestionDTO questionDto : dto.getQuestions()) {

            Question question = null;
            if (questionDto.getId() != null) {
                // Merge existing questionDto, no need to reassign to the variable
                question = questionRepository.findById(questionDto.getId()).orElse(null);
            }
            if (question == null) {
                question = new Question();
            }
            question.setAudio(questionDto.getAudio());
            question.setImage(questionDto.getImage());
            question.setExplanation(questionDto.getExplanation());
            question.setQuestion(questionDto.getQuestion());
            question.setLevel(questionDto.getLevel());
            question.setSkill(questionDto.getSkill());
            question.setExam(entity);

            if(question.getChoises()!=null) {
                question.getChoises().clear();
            }else {
                question.setChoises(new ArrayList<>());
            }
            if(questionDto.getChoises()!=null) {
                for (ChoiseDTO choiseDTO : questionDto.getChoises()) {
                    Choise choise = null;
                    if (choiseDTO.getId() != null) {
                        choise = choiseRepository.findById(choiseDTO.getId()).orElse(null);
                    }
                    if (choise == null) {
                        choise = new Choise();
                    }
                    choise.setQuestion(question);
                    choise.setAnswer(choiseDTO.getAnswer());
                    choise.setCorrect(choiseDTO.isCorrect());
                    question.getChoises().add(choise);
                }
            }

            entity.getQuestions().add(question); // Add the question to the exam
        }

        // Save or update the exam
        return repository.save(entity);
    }
}
