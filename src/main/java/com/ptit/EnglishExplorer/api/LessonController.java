package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.data.entity.History;
import com.ptit.EnglishExplorer.data.entity.Lesson;
import com.ptit.EnglishExplorer.data.service.HistoryService;
import com.ptit.EnglishExplorer.data.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/lessons")
public class LessonController extends BaseController<Lesson, Long, LessonService> {

    @Autowired
    public LessonController(LessonService service) {
        super(service);
    }
}
