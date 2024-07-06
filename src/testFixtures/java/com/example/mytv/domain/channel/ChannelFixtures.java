package com.example.mytv.domain.channel;

import com.example.mytv.domain.user.UserFixtures;

public class ChannelFixtures {
    public static Channel stub(String id) {
        return Channel.builder()
            .id(id)
            .snippet(ChannelSnippetFixtures.stub())
            .statistics(ChannelStatisticsFixtures.stub())
            .contentOwner(UserFixtures.channelOwner())
            .build();
    }

    public static Channel anotherStub(String id) {
        return Channel.builder()
            .id(id)
            .snippet(ChannelSnippetFixtures.anotherStub())
            .statistics(ChannelStatisticsFixtures.stub())
            .contentOwner(UserFixtures.channelOwner())
            .build();
    }
}
