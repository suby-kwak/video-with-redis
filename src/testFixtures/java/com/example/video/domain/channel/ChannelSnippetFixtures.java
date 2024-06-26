package com.example.video.domain.channel;

import java.time.ZonedDateTime;

public class ChannelSnippetFixtures {
    public static ChannelSnippet stub() {
        return ChannelSnippet.builder()
            .title("Channel Title")
            .description("Channel Description")
            .publishedAt(ZonedDateTime.parse("2024-06-01T00:00:00Z"))
            .thumbnailUrl("https://example.com/thumbnail.jpg")
            .build();
    }
}
