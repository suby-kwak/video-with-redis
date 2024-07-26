package com.example.mytv.adapter.in.api;

import com.example.mytv.adapter.in.api.dto.CommandResponse;
import com.example.mytv.adapter.in.api.dto.CommentRequest;
import com.example.mytv.application.port.in.CommentUseCase;
import com.example.mytv.domain.user.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentApiController {
    private final CommentUseCase commentUseCase;

    public CommentApiController(CommentUseCase commentUseCase) {
        this.commentUseCase = commentUseCase;
    }

    @PostMapping
    CommandResponse createComment(
        User user,
        @RequestBody CommentRequest commentRequest
    ) {
        var comment = commentUseCase.createComment(user, commentRequest);
        return new CommandResponse(comment.getId());
    }

    @PutMapping("{commentId}")
    CommandResponse updateComment(
        User user,
        @PathVariable String commentId,
        @RequestBody CommentRequest commentRequest
    ) {
        var updateComment = commentUseCase.updateComment(commentId, user, commentRequest);
        return new CommandResponse(updateComment.getId());
    }
}
