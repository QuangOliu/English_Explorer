package com.ptit.EnglishExplorer.data.dto;


import com.ptit.EnglishExplorer.data.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    private Long id;

    private String name;

    private String description;

    private Long numberOfStudents = 0l;

    private Long numberOfLessons = 0l;

    private Long numberOfCompletedLessons = 0l;

    public CourseDto(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.description = course.getDescription();
    }
}
