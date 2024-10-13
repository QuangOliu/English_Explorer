package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.data.entity.Course;
import com.ptit.EnglishExplorer.data.entity.History;
import com.ptit.EnglishExplorer.data.service.CourseService;
import com.ptit.EnglishExplorer.data.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/histories")
public class HistoryController extends BaseController<History, Long, HistoryService> {

    @Autowired
    public HistoryController(HistoryService service) {
        super(service);
    }
}
