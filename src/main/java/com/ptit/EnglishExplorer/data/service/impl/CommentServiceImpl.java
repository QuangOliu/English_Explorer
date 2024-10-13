package com.ptit.EnglishExplorer.data.service.impl;

import com.ptit.EnglishExplorer.data.entity.Choise;
import com.ptit.EnglishExplorer.data.entity.Comment;
import com.ptit.EnglishExplorer.data.repository.ChoiseRepository;
import com.ptit.EnglishExplorer.data.repository.CommentRepository;
import com.ptit.EnglishExplorer.data.service.ChoiseService;
import com.ptit.EnglishExplorer.data.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CommentServiceImpl extends BaseServiceImpl<Comment, Long, CommentRepository> implements CommentService {

    @Autowired
    public CommentServiceImpl(CommentRepository repository) {
        super(repository);
    }
}
