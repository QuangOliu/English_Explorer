package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.data.entity.Course;
import com.ptit.EnglishExplorer.data.repository.CourseRepository;
import com.ptit.EnglishExplorer.data.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl extends BaseServiceImpl<Course, Long, CourseRepository> implements CourseService {

    @Autowired
    public CourseServiceImpl(CourseRepository repository) {
        super(repository);
    }

}
