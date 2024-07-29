package com.example.mytv.domain.comment;

import com.example.mytv.domain.user.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class CommentResponse {
    private String id;
    private String channelId;
    private String videoId;
    private String parentId;
    private String text;
    private LocalDateTime publishedAt;
    private String authorId;
    private String authorName;
    private String authorProfileImageUrl;
    private Long likeCount;

    public static CommentResponse from(Comment comment, User author, Long likeCount) {
        return CommentResponse.builder()
            .id(comment.getId())
            .channelId(comment.getChannelId())
            .videoId(comment.getVideoId())
            .parentId(comment.getParentId())
            .text(comment.getText())
            .publishedAt(comment.getPublishedAt())
            .authorId(author.getId())
            .authorName(author.getName())
            .authorProfileImageUrl(author.getProfileImageUrl())
            .likeCount(likeCount)
            .build();
    }
}
