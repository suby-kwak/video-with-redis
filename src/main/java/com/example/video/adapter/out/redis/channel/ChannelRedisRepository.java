package com.example.video.adapter.out.redis.channel;

import com.example.video.adapter.out.redis.channel.ChannelRedisHash;
import org.springframework.data.repository.CrudRepository;

public interface ChannelRedisRepository extends CrudRepository<ChannelRedisHash, String> {
}
