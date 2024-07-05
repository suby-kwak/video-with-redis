package com.example.video.adapter.out.jpa.video;

import com.example.video.adapter.out.jpa.channel.ChannelJpaEntityFixtures;
import java.time.ZonedDateTime;

public class VideoJpaEntityFixtures {
    public static VideoJpaEntity stub(String id) {
        return new VideoJpaEntity(
            id,
            "title",
            "description",
            "https://example.com/thumbnail",
            ChannelJpaEntityFixtures.stub("channelId"),
            ZonedDateTime.now()
        );
    }
}
