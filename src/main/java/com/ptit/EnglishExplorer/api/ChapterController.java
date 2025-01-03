package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.data.entity.Action;
import com.ptit.EnglishExplorer.data.entity.Chapter;
import com.ptit.EnglishExplorer.data.service.ActionService;
import com.ptit.EnglishExplorer.data.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/chapter")
public class ChapterController extends BaseController<Chapter, Long, ChapterService> {
    public ChapterController(ChapterService service) {
        super(service);
    }

    @GetMapping("/get-by-course/{id}")
    public List<Chapter> getByCourse(@PathVariable Long id) {
        return service.getByCourse(id);
    }
}
