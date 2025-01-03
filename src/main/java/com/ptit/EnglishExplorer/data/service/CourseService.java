package com.ptit.EnglishExplorer.data.service;

import com.ptit.EnglishExplorer.data.entity.Course;

import java.util.List;

public interface CourseService extends CrudService<Course, Long> {
    List<Course> getByClassroomId(Long id);
}
