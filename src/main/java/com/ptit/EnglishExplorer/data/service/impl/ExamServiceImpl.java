package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.data.entity.Classroom;
import com.ptit.EnglishExplorer.data.entity.Exam;
import com.ptit.EnglishExplorer.data.entity.Lesson;
import com.ptit.EnglishExplorer.data.entity.Question;
import com.ptit.EnglishExplorer.data.repository.ClassroomRepository;
import com.ptit.EnglishExplorer.data.repository.ExamRepository;
import com.ptit.EnglishExplorer.data.service.ExamService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ExamServiceImpl extends BaseServiceImpl<Exam, Long, ExamRepository> implements ExamService {
    @Autowired
    private ClassroomRepository classroomRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    public ExamServiceImpl(ExamRepository repository) {
        super(repository);
    }

    @Override
    public List<Exam> getExamsByClassroomId(Long classroomId) {
        return repository.findByClassroomId(classroomId);
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
