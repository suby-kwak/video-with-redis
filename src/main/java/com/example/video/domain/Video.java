package com.example.video.domain;

import com.example.video.domain.channel.Channel;
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
    private String thumbnail;
    private Channel channel;
    private LocalDateTime publishedAt;
}
