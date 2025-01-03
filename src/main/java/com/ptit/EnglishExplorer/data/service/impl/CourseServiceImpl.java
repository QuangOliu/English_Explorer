package com.ptit.EnglishExplorer.data.service.impl;

import com.google.auto.value.AutoValue;
import com.ptit.EnglishExplorer.data.entity.Classroom;
import com.ptit.EnglishExplorer.data.entity.Course;
import com.ptit.EnglishExplorer.data.repository.ClassroomRepository;
import com.ptit.EnglishExplorer.data.repository.CourseRepository;
import com.ptit.EnglishExplorer.data.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl extends BaseServiceImpl<Course, Long, CourseRepository> implements CourseService {

    @Autowired
    private ClassroomRepository classroomRepository;

    public CourseServiceImpl(CourseRepository repository) {
        super(repository);
    }

    @Override
    public List<Course> getByClassroomId(Long id) {
        return repository.getByClassroom(id);
    }

    @Override
    public Course save(Course course) {
        Course newCourse = null;
        if (course.getId() != null) {
            newCourse = repository.findById(course.getId()).orElse(null);
        }

        if (newCourse == null) {
            newCourse = new Course();
        }

        newCourse.setName(course.getName());
        newCourse.setDescription(course.getDescription());

        Classroom classroom = null;
        if (course.getClassroom() != null && course.getClassroom().getId() != null) {
            classroom = classroomRepository.findById(course.getClassroom().getId()).orElse(null);
        }

        newCourse.setClassroom(classroom);
        return repository.save(course);
    }
}
