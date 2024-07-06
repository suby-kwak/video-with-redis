package com.example.video.adapter.out.jpa.video;

import com.example.video.adapter.out.jpa.channel.ChannelJpaEntityFixtures;
import java.time.LocalDateTime;

public class VideoJpaEntityFixtures {
    public static VideoJpaEntity stub(String id) {
        return new VideoJpaEntity(
            id,
            "title",
            "description",
            "https://example.com/thumbnail",
            ChannelJpaEntityFixtures.stub("channelId"),
            LocalDateTime.now()
        );
    }
}
