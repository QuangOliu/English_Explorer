package com.ptit.EnglishExplorer.data.service;

import com.ptit.EnglishExplorer.data.entity.Comment;

import java.util.List;

public interface CommentService extends CrudService<Comment, Long> {
    List<Comment> getCommentByQuestion(Long id);
}
