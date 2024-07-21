package com.example.mytv.adapter.out;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.mytv.adapter.out.jpa.video.VideoJpaRepository;
import com.example.mytv.config.TestRedisConfig;
import com.example.mytv.domain.Video;
import com.example.mytv.util.RedisKeyGenerator;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = TestRedisConfig.class)
@Transactional
@Sql("/com/example/mytv/adapter/out/VideoPersistenceAdapterIntTest.sql")
public class VideoPersistenceAdapterIntTest {
    @Autowired
    private VideoPersistenceAdapter sut;

    @SpyBean
    private VideoJpaRepository videoJpaRepository;
    @Autowired
    private RedisTemplate<String, Long> redisTemplate;
    @Autowired
    @Qualifier("redisListCacheManager")
    private RedisCacheManager redisListCacheManager;

    @Test
    void loadVideo() {
        var videoId = "video1";
        for (int i = 0; i < 3; i++) {
            sut.loadVideo(videoId);
        }

        verify(videoJpaRepository, times(1)).findById(videoId);
    }

    @Test
    void loadVideoByChannel() {
        for (int i = 0; i < 3; i++) {
            sut.loadVideoByChannel("channelId");
        }

        verify(videoJpaRepository, times(1)).findByChannelId("channelId");
    }

    @Test
    void createVideoThenEvictVideoListCache() {
        // Given
        sut.loadVideoByChannel("channel1");
        System.out.println(redisListCacheManager.getCache("video:list").get("channel1"));

        var video = Video.builder()
            .id("video3").title("video3").description("video3").thumbnailUrl("https://example.com/image.jpg")
            .channelId("channel1").publishedAt(LocalDateTime.now())
            .build();

        // When
        sut.createVideo(video);

        // Then
        then(redisListCacheManager.getCache("video:list").get("channel1"))
            .isNull();
    }

    @Test
    void incrVideoViewCount() {
        for (int i = 0; i < 3; i++) {
            sut.incrementViewCount("video1");
        }

        var result = redisTemplate.opsForValue().get(RedisKeyGenerator.getVideoViewCountKey("video1"));
        then(result).isEqualTo(3L);
    }
}
