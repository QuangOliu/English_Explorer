package com.ptit.EnglishExplorer.api;

import com.ptit.EnglishExplorer.data.entity.Comment;
import com.ptit.EnglishExplorer.data.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/comments")
public class CommentController extends BaseController<Comment, Long, CommentService> {

    @Autowired
    public CommentController(CommentService service) {
        super(service);
    }

    @GetMapping("/question/{id}")
    public List<Comment> getCommentByQuestion(@PathVariable Long id) {
        return service.getCommentByQuestion(id);
    }
}
