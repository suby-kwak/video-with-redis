package com.example.mytv.adapter.out.redis.channel;

import com.example.mytv.adapter.out.redis.user.UserRedisHash;
import com.example.mytv.domain.channel.Channel;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("channel")
@AllArgsConstructor
@Getter
public class ChannelRedisHash implements Serializable {
    @Id
    private String id;
    private ChannelSnippetRedisHash snippet;
    private ChannelStatisticsRedisHash statistics;
    private UserRedisHash contentOwner;

    public Channel toDomain() {
        return Channel.builder()
            .id(this.getId())
            .snippet(this.getSnippet().toDomain())
            .statistics(this.getStatistics().toDomain())
            .contentOwner(this.contentOwner.toDomain())
            .build();
    }

    public static ChannelRedisHash from(Channel channel) {
        return new ChannelRedisHash(
            channel.getId(),
            ChannelSnippetRedisHash.from(channel.getSnippet()),
            ChannelStatisticsRedisHash.from(channel.getStatistics()),
            UserRedisHash.from(channel.getContentOwner())
        );
    }
}
