package com.example.video.adapter.out.redis;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Channel")
@AllArgsConstructor
@Getter
public class ChannelRedisHash implements Serializable {
    private String id;
}
