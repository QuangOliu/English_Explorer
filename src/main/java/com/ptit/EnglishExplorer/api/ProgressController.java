package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.data.entity.Choise;
import com.ptit.EnglishExplorer.data.entity.Lesson;
import com.ptit.EnglishExplorer.data.entity.Progress;
import com.ptit.EnglishExplorer.data.service.ChoiseService;
import com.ptit.EnglishExplorer.data.service.ProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/progress")
public class ProgressController extends BaseController<Progress, Long, ProgressService> {

    @Autowired
    public ProgressController(ProgressService service) {
        super(service);
    }

    @PostMapping("/mark-as-completed")
    public Progress markAsCompleted(@RequestBody Lesson lesson) {
        return service.markAsCompleted(lesson);
    }

}
