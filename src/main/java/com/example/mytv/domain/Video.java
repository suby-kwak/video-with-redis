package com.example.mytv.domain;

import com.example.mytv.domain.channel.Channel;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Video {
    private String id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private Channel channel;
    private Long viewCount;
    private LocalDateTime publishedAt;

    public void bindViewCount(long viewCount) {
        this.viewCount = viewCount;
    }
}
