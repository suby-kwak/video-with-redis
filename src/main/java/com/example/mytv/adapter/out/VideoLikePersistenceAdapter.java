package com.example.mytv.adapter.out;

import com.example.mytv.application.port.out.VideoLikePort;
import com.example.mytv.util.RedisKeyGenerator;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class VideoLikePersistenceAdapter implements VideoLikePort {
    private final StringRedisTemplate stringRedisTemplate;

    public VideoLikePersistenceAdapter(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Long addVideoLike(String videoId, String userId) {
        return stringRedisTemplate.opsForSet().add(RedisKeyGenerator.getVideoLikeKey(videoId), userId);
    }

    @Override
    public Long removeVideoLike(String videoId, String userId) {
        return stringRedisTemplate.opsForSet().remove(RedisKeyGenerator.getVideoLikeKey(videoId), userId);
    }
}
