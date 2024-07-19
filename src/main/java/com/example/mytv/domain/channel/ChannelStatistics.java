package com.example.mytv.domain.channel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ChannelStatistics {
    private long videoCount;
    private long subscriberCount;
    private long commentCount;
}
