package com.example.mytv.application.port.out;

import com.example.mytv.domain.comment.Comment;
import java.util.Optional;

public interface CommentPort {
    Comment saveComment(Comment comment);

    Optional<Comment> loadComment(String commentId);
}
