package com.example.mytv.adapter.out.jpa.video;

import com.example.mytv.adapter.out.jpa.channel.ChannelJpaEntityFixtures;
import java.time.LocalDateTime;

public class VideoJpaEntityFixtures {
    public static VideoJpaEntity stub(String id) {
        return new VideoJpaEntity(
            id,
            "title",
            "description",
            "https://example.com/thumbnail",
            "channelId",
            LocalDateTime.now()
        );
    }
}
