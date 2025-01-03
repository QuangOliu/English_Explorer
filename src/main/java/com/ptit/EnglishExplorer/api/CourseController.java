package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.data.entity.Course;
import com.ptit.EnglishExplorer.data.service.CourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/course")
public class CourseController extends BaseController<Course, Long, CourseService> {
    public CourseController(CourseService service) {
        super(service);
    }

    @GetMapping("/get-by-classroom/{id}")
    public List<Course> getByClassroom(@PathVariable Long id) {
        return service.getByClassroomId(id);
    }
}