package com.example.mytv.domain.comment;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class Comment {
    private String id;
    private String channelId;
    private String videoId;
    private String authorId;
    private String text;
    private LocalDateTime publishedAt;

    public void updateText(String text) {
        this.text = text;
    }
}
