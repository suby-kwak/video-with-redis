package com.example.video.domain.channel;

public class ChannelStatisticsFixtures {
    public static ChannelStatistics stub() {
        return ChannelStatisticsFixtures.stub(100, 5, 10, 20);
    }

    public static ChannelStatistics stub(int viewCount, int videoCount, int subscriberCount, int commentCount) {
        return ChannelStatistics.builder()
            .viewCount(viewCount)
            .subscriberCount(subscriberCount)
            .videoCount(videoCount)
            .commentCount(commentCount)
            .build();
    }
}
