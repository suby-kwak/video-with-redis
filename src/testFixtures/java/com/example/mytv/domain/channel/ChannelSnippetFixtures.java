package com.example.mytv.domain.channel;

import java.time.LocalDateTime;

public class ChannelSnippetFixtures {
    public static ChannelSnippet stub() {
        return ChannelSnippet.builder()
            .title("Channel Title")
            .description("Channel Description")
            .thumbnailUrl("https://example.com/thumbnail.jpg")
            .publishedAt(LocalDateTime.now())
            .build();
    }
}
