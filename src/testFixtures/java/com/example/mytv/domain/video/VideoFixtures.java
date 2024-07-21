package com.example.mytv.domain.video;

import com.example.mytv.domain.Video;
import com.example.mytv.domain.channel.ChannelFixtures;

public class VideoFixtures {
    public static Video stub(String id) {
        return Video.builder()
            .id(id)
            .title("video title")
            .description("video description")
            .thumbnailUrl("https://example.com/thumbnail.jpg")
            .channelId("channelId")
            .viewCount(0L)
            .build();
    }
}
