package com.example.video.adapter.out.jpa.channel;

import java.time.ZonedDateTime;

public class ChannelJpaEntityFixtures {
    public static ChannelJpaEntity stub(String id) {
        return new ChannelJpaEntity(
            id,
            new ChannelSnippetJpaEntity("title", "description", ZonedDateTime.now(), "thumbnails"),
            new ChannelStatisticsJpaEntity(10, 10, 10, 10)
        );
    }
}
