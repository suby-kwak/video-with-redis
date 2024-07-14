package com.example.mytv.adapter.out;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.mytv.adapter.out.jpa.video.VideoJpaRepository;
import com.example.mytv.config.TestRedisConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
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
    void incrVideoViewCount() {
        for (int i = 0; i < 3; i++) {
            sut.incrementViewCount("video1");
        }

        var result = redisTemplate.opsForValue().get("video1:viewCount");
        then(result).isEqualTo(3L);
    }
}
