package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.auditing.ApplicationAuditAware;
import com.ptit.EnglishExplorer.data.entity.Comment;
import com.ptit.EnglishExplorer.data.entity.User;
import com.ptit.EnglishExplorer.data.repository.CommentRepository;
import com.ptit.EnglishExplorer.data.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment, Long, CommentRepository> implements CommentService {

    @Autowired
    public CommentServiceImpl(CommentRepository repository) {
        super(repository);
    }

    @Override
    public List<Comment> getCommentByQuestion(Long id) {
        return repository.getCommentByQuestionId(id);
    }

    @Override
    public Comment save(Comment comment) {
        User user = ApplicationAuditAware.getCurrentUser();
        comment.setUser(user);

        return repository.save(comment);
    }
}
