package com.example.mytv.application;

import com.example.mytv.adapter.in.api.dto.CommentRequest;
import com.example.mytv.application.port.in.CommentUseCase;
import com.example.mytv.application.port.out.CommentLikePort;
import com.example.mytv.application.port.out.CommentPort;
import com.example.mytv.application.port.out.LoadUserPort;
import com.example.mytv.domain.comment.Comment;
import com.example.mytv.domain.comment.CommentResponse;
import com.example.mytv.domain.user.User;
import com.example.mytv.exception.BadRequestException;
import com.example.mytv.exception.DomainNotFoundException;
import com.example.mytv.exception.ForbiddenRequestException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CommentService implements CommentUseCase {
    private final CommentPort commentPort;
    private final LoadUserPort loadUserPort;
    private final CommentLikePort commentLikePort;

    public CommentService(
        CommentPort commentPort,
        @Qualifier("userCachePersistenceAdapter") LoadUserPort loadUserPort,
        CommentLikePort commentLikePort)
    {
        this.commentPort = commentPort;
        this.loadUserPort = loadUserPort;
        this.commentLikePort = commentLikePort;
    }

    @Override
    public Comment createComment(User user, CommentRequest commentRequest) {
        var comment = Comment.builder()
            .id(UUID.randomUUID().toString())
            .channelId(commentRequest.getChannelId())
            .videoId(commentRequest.getVideoId())
            .parentId(commentRequest.getParentId())
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

    /**
     * user -> redis user:{userId} 로 조회
     * commentLike -> redis comment:like:{commentId} 로 부터 조회
     */
    @Override
    public CommentResponse getComment(String commentId) {
        var comment = commentPort.loadComment(commentId)
            .orElseThrow(() -> new DomainNotFoundException("Comment Not Found."));
        var user = loadUserPort.loadUser(comment.getAuthorId())
            .orElse(User.defaultUser(comment.getAuthorId()));
        var commentLikeCount = commentLikePort.getCommentLikeCount(comment.getId());

        return CommentResponse.from(comment, user, commentLikeCount);
    }

    @Override
    public List<CommentResponse> listComments(String videoId, String order, String offset, Integer maxSize) {
        var list = commentPort.listComment(videoId, order, offset, maxSize).stream()
            .map(comment -> {
                var user = loadUserPort.loadUser(comment.getAuthorId())
                    .orElse(User.defaultUser(comment.getAuthorId()));
                var commentLikeCount = commentLikePort.getCommentLikeCount(comment.getId());
                return CommentResponse.from(comment, user, commentLikeCount);
            })
            .collect(Collectors.toList());
        commentPort.getPinnedComment(videoId)
            .ifPresent(pinnedComment -> {
                var user = loadUserPort.loadUser(pinnedComment.getAuthorId())
                    .orElse(User.defaultUser(pinnedComment.getAuthorId()));
                var commentLikeCount = commentLikePort.getCommentLikeCount(pinnedComment.getId());
                var pinnedCommentResponse = CommentResponse.from(pinnedComment, user, commentLikeCount);

                list.add(0, pinnedCommentResponse);
            });

        return list;
    }

    @Override
    public List<CommentResponse> listReplies(String parentId, String offset, Integer maxSize) {
        return commentPort.listReply(parentId, offset, maxSize).stream()
            .map(comment -> {
                var user = loadUserPort.loadUser(comment.getAuthorId())
                    .orElse(User.defaultUser(comment.getAuthorId()));
                var commentLikeCount = commentLikePort.getCommentLikeCount(comment.getId());
                return CommentResponse.from(comment, user, commentLikeCount);
            })
            .collect(Collectors.toList());
    }

    private boolean equalMetaData(Comment comment, CommentRequest commentRequest) {
        return Objects.equals(comment.getChannelId(), commentRequest.getChannelId()) &&
            Objects.equals(comment.getVideoId(), commentRequest.getVideoId());
    }

//    private CommentResponse buildComment(Comment comment) {
//        var user = loadUserPort.loadUser(comment.getAuthorId())
//                .orElse(User.defaultUser(comment.getAuthorId()));
//        var commentLikeCount = commentLikePort.getCommentLikeCount(comment.getId());
//        return CommentResponse.from(comment, user, commentLikeCount);
//    }

//    var replies = commentPort.listReply(parentId, offset, maxSize);
//    var users = getUserMap(replies.stream().map(Comment::getAuthorId).toList());
//
//    private Map<String, User> getUserMap(List<String> userIds) {
//        return loadUserPort.loadAllUsers(userIds).stream()
//            .collect(Collectors.toMap(User::getId, Function.identity()));
//    }
}
