package com.example.mytv.domain.channel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ChannelStatistics {
    private int viewCount;
    private int videoCount;
    private int subscriberCount;
    private int commentCount;
}
