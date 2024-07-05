package com.example.video.domain.channel;

import com.example.video.adapter.out.redis.channel.ChannelRedisHash;
import com.example.video.domain.User;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class Channel implements Serializable {
    private String id;
    private ChannelSnippet snippet;
    private ChannelStatistics statistics;
    private User contentOwner;

    public static Channel from(ChannelRedisHash channel) {
        return Channel.builder()
                .id(channel.getId())
                .build();
    }
}
