package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.auditing.ApplicationAuditAware;
import com.ptit.EnglishExplorer.data.dto.CourseDto;
import com.ptit.EnglishExplorer.data.entity.Classroom;
import com.ptit.EnglishExplorer.data.entity.Course;
import com.ptit.EnglishExplorer.data.entity.User;
import com.ptit.EnglishExplorer.data.repository.ClassroomRepository;
import com.ptit.EnglishExplorer.data.repository.CourseRepository;
import com.ptit.EnglishExplorer.data.repository.LessonRepository;
import com.ptit.EnglishExplorer.data.repository.ProgressRepository;
import com.ptit.EnglishExplorer.data.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl extends BaseServiceImpl<Course, Long, CourseRepository> implements CourseService {

    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private ProgressRepository progressRepository;
    @Autowired
    private LessonRepository lessonRepository;

    public CourseServiceImpl(CourseRepository repository) {
        super(repository);
    }

    @Override
    public List<CourseDto> getByClassroomId(Long id) {
        List<Course> listCourse = repository.getByClassroom(id);
        User user = ApplicationAuditAware.getCurrentUser();

        List<CourseDto> listCourseDto = new ArrayList<>();

        for(Course course : listCourse) {
            CourseDto courseDto = new CourseDto(course);
            Long numberOfCompletedLessons = progressRepository.countCompletedLessonsByUserAndCourse(user.getId(), course.getId());
            Long numberOfLessons = lessonRepository.findByCourseId(course.getId());

            courseDto.setNumberOfCompletedLessons(numberOfCompletedLessons);
            courseDto.setNumberOfLessons(numberOfLessons);
            listCourseDto.add(courseDto);
        }

        return listCourseDto;

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
