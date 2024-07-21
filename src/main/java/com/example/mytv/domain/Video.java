package com.example.mytv.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class Video {
    private String id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String channelId;
    private Long viewCount;
    private LocalDateTime publishedAt;

    public void bindViewCount(long viewCount) {
        this.viewCount = viewCount;
    }
}
