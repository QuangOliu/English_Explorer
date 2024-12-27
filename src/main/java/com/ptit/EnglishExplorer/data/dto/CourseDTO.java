package com.ptit.EnglishExplorer.data.dto;

import com.ptit.EnglishExplorer.data.entity.Classroom;
import com.ptit.EnglishExplorer.data.entity.Course;
import com.ptit.EnglishExplorer.data.entity.Lesson;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
    private Long id;
    private String name;
    UserDTO user;
    private Set<LessonDTO> lessons = new HashSet<>();
    private Set<ClassroomDTO> classrooms = new HashSet<>();

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.user = new UserDTO(course.getUser());
        if(course.getLessons() != null) {
            for(Lesson lesson : course.getLessons()) {
                this.lessons.add(new LessonDTO(lesson));
            }
        }
        if(course.getClassrooms() != null) {
            for(Classroom classroom : course.getClassrooms()) {
                this.classrooms.add(new ClassroomDTO(classroom));
            }
        }
    }

}
