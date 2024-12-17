package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.data.entity.Action;
import com.ptit.EnglishExplorer.data.entity.UserExam;
import com.ptit.EnglishExplorer.data.service.ActionService;
import com.ptit.EnglishExplorer.data.service.UserExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/userexams")
public class UserExamController extends BaseController<UserExam, Long, UserExamService> {
    @Autowired
    public UserExamController(UserExamService service) {
        super(service);
    }

    @GetMapping(path = "/list-user/{id}")
    public List<UserExam> getAllUserInExams(@PathVariable("id") Long id) {
        return service.getAllUserExamInExams(id);
    }
}
