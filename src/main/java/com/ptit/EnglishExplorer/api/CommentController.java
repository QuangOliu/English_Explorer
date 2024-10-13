package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.data.entity.Choise;
import com.ptit.EnglishExplorer.data.entity.Comment;
import com.ptit.EnglishExplorer.data.service.ChoiseService;
import com.ptit.EnglishExplorer.data.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/comments")
public class CommentController extends BaseController<Comment, Long, CommentService> {

    @Autowired
    public CommentController(CommentService service) {
        super(service);
    }
}
