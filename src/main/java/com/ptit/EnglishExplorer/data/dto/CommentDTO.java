package com.ptit.EnglishExplorer.data.dto;

import com.ptit.EnglishExplorer.data.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    UserDTO user;
    QuestionDTO question;
    private String content;
    CommentDTO parent;

    public CommentDTO(Comment comment) {
        this(comment, true);
    }

    public CommentDTO(Comment comment, boolean fullInfo) {
        this.id = comment.getId();
        this.user = new UserDTO(comment.getUser());
        this.question = new QuestionDTO(comment.getQuestion());
        this.content = comment.getContent();
        if (fullInfo) {
            this.parent = new CommentDTO(comment.getParent(), false);
        }
    }
}
