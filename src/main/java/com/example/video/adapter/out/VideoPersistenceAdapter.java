package com.example.video.adapter.out;

import static com.example.video.config.RedisCacheNames.VIDEO;
import static com.example.video.config.RedisCacheNames.VIDEO_LIST;

import com.example.video.adapter.out.jpa.video.VideoJpaEntity;
import com.example.video.adapter.out.jpa.video.VideoJpaRepository;
import com.example.video.application.port.out.LoadVideoPort;
import com.example.video.domain.Video;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VideoPersistenceAdapter implements LoadVideoPort {
    private final VideoJpaRepository videoJpaRepository;

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
}
