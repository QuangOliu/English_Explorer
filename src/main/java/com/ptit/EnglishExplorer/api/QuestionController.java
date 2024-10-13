package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.data.entity.Notification;
import com.ptit.EnglishExplorer.data.entity.Question;
import com.ptit.EnglishExplorer.data.service.NotificationService;
import com.ptit.EnglishExplorer.data.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/questions")
public class QuestionController extends BaseController<Question, Long, QuestionService> {

    @Autowired
    public QuestionController(QuestionService service) {
        super(service);
    }
}
