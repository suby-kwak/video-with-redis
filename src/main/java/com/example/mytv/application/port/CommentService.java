package com.example.mytv.application.port;

import com.example.mytv.adapter.in.api.dto.CommentRequest;
import com.example.mytv.application.port.in.CommentUseCase;
import com.example.mytv.application.port.out.CommentPort;
import com.example.mytv.domain.comment.Comment;
import com.example.mytv.domain.user.User;
import com.example.mytv.exception.BadRequestException;
import com.example.mytv.exception.DomainNotFoundException;
import com.example.mytv.exception.ForbiddenRequestException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class CommentService implements CommentUseCase {
    private final CommentPort commentPort;

    public CommentService(CommentPort commentPort) {
        this.commentPort = commentPort;
    }

    @Override
    public Comment createComment(User user, CommentRequest commentRequest) {
        var comment = Comment.builder()
            .id(UUID.randomUUID().toString())
            .channelId(commentRequest.getChannelId())
            .videoId(commentRequest.getVideoId())
            .text(commentRequest.getText())
            .authorId(user.getId())
            .publishedAt(LocalDateTime.now())
            .build();

        return commentPort.saveComment(comment);
    }

    @Override
    public Comment updateComment(String commentId, User user, CommentRequest commentRequest) {
        var comment = commentPort.loadComment(commentId)
            .orElseThrow(() -> new DomainNotFoundException("Comment Not Found."));

        if (!Objects.equals(comment.getAuthorId(), user.getId())) {
            throw new ForbiddenRequestException("The request might not be properly authorized.");
        }
        if (!equalMetaData(comment, commentRequest)) {
            throw new BadRequestException("Request metadata is invalid.");
        }

        comment.updateText(commentRequest.getText());

        return commentPort.saveComment(comment);
    }

    @Override
    public void deleteComment(String commentId, User user) {
        var comment = commentPort.loadComment(commentId)
            .orElseThrow(() -> new DomainNotFoundException("Comment Not Found."));

        if (!Objects.equals(comment.getAuthorId(), user.getId())) {
            throw new ForbiddenRequestException("The request might not be properly authorized.");
        }

        commentPort.deleteComment(commentId);
    }

    private boolean equalMetaData(Comment comment, CommentRequest commentRequest) {
        return Objects.equals(comment.getChannelId(), commentRequest.getChannelId()) &&
            Objects.equals(comment.getVideoId(), commentRequest.getVideoId());
    }
}
