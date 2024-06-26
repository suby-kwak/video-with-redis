package com.example.video.domain.channel;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ChannelSnippet {
    private String title;
    private String description;
    private ZonedDateTime publishedAt;
    private String thumbnailUrl;
}
