package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.data.entity.Course;
import com.ptit.EnglishExplorer.data.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/courses")
public class CourseController extends BaseController<Course, Long, CourseService> {

    @Autowired
    public CourseController(CourseService service) {
        super(service);
    }
}
