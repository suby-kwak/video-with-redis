package com.example.mytv.adapter.out.mongo.comment;

import com.example.mytv.domain.comment.Comment;
import java.time.LocalDateTime;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("comment")
@AllArgsConstructor
@Getter
public class CommentDocument {
    @Id
    private String id;
    private String channelId;
    private String videoId;
    private String authorId;
    private String text;
    @Indexed
    private LocalDateTime publishedAt;

    public static CommentDocument from(Comment comment) {
        return new CommentDocument(
            comment.getId(),
            comment.getChannelId(),
            comment.getVideoId(),
            comment.getAuthorId(),
            comment.getText(),
            comment.getPublishedAt()
        );
    }

    public Comment toDomain() {
        return Comment.builder()
            .id(this.getId())
            .channelId(this.getChannelId())
            .videoId(this.getVideoId())
            .text(this.getText())
            .authorId(this.authorId)
            .publishedAt(this.getPublishedAt())
            .build();
    }
}
