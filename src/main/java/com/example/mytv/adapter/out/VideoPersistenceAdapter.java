package com.example.mytv.adapter.out;

import static com.example.mytv.config.RedisCacheNames.VIDEO;
import static com.example.mytv.config.RedisCacheNames.VIDEO_LIST;

import com.example.mytv.adapter.out.jpa.video.VideoJpaEntity;
import com.example.mytv.adapter.out.jpa.video.VideoJpaRepository;
import com.example.mytv.application.port.out.LoadVideoPort;
import com.example.mytv.application.port.out.SaveVideoPort;
import com.example.mytv.domain.Video;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoPersistenceAdapter implements LoadVideoPort, SaveVideoPort {
    private final VideoJpaRepository videoJpaRepository;
    private final RedisTemplate<String, Long> redisTemplate;

    @Override
    @Cacheable(cacheNames = VIDEO, key = "#videoId")
    public Video loadVideo(String videoId) {
        return videoJpaRepository.findById(videoId)
            .map(VideoJpaEntity::toDomain)
            .orElseThrow();
    }

    @Override
    @Cacheable(cacheManager = "redisListCacheManager", cacheNames = VIDEO_LIST, key = "#channelId")
    public List<Video> loadVideoByChannel(String channelId) {
        return videoJpaRepository.findByChannelId(channelId).stream()
            .map(VideoJpaEntity::toDomain)
            .toList();
    }

    @Override
    public void incrementViewCount(String videoId) {
        var videoViewCountKey = getVideoViewCountKey(videoId);
        redisTemplate.opsForValue().increment(videoViewCountKey);

//        // Using RedisAtomicLong
//        RedisAtomicLong redisAtomicLong = new RedisAtomicLong(videoViewCountKey, redisTemplate.getConnectionFactory());
//        redisAtomicLong.incrementAndGet()
    }

    @Override
    public Long getViewCount(String videoId) {
        var videoViewCountKey = getVideoViewCountKey(videoId);
        return redisTemplate.opsForValue().get(videoViewCountKey);
    }

    private String getVideoViewCountKey(String videoId) {
        return videoId + ":viewCount";
    }
}
