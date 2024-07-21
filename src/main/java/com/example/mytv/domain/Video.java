package com.example.mytv.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Video implements Serializable {
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
