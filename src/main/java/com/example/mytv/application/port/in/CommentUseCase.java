package com.example.mytv.application.port.in;

import com.example.mytv.adapter.in.api.dto.CommentRequest;
import com.example.mytv.domain.comment.Comment;
import com.example.mytv.domain.user.User;

public interface CommentUseCase {
    Comment createComment(User user, CommentRequest commentRequest);

    Comment updateComment(String commentId, User user, CommentRequest commentRequest);

    void deleteComment(String commentId, User user);
}
