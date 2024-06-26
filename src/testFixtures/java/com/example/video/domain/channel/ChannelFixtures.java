package com.example.video.domain.channel;

import com.example.video.domain.user.UserFixtures;

public class ChannelFixtures {
    public static Channel stub(String id) {
        return Channel.builder()
            .id(id)
            .statistics(ChannelStatisticsFixtures.stub())
            .snippet(ChannelSnippetFixtures.stub())
            .contentOwner(UserFixtures.channelOwner())
            .build();
    }
}
