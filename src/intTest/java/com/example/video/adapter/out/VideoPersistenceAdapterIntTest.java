package com.example.video.adapter.out;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.video.adapter.out.jpa.video.VideoJpaEntityFixtures;
import com.example.video.adapter.out.jpa.video.VideoJpaRepository;
import com.example.video.config.TestRedisConfig;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = TestRedisConfig.class)
@Transactional
@ExtendWith(MockitoExtension.class)
public class VideoPersistenceAdapterIntTest {
    @Autowired
    private VideoPersistenceAdapter sut;

    @SpyBean
    private VideoJpaRepository videoJpaRepository;

    @Test
    void loadVideo() {
        // Given
        var videoId = "video1";
        given(videoJpaRepository.findById(videoId))
            .willReturn(Optional.of(VideoJpaEntityFixtures.stub(videoId)));

        // When
        for (int i = 0; i < 3; i++) {
            sut.loadVideo(videoId);
        }

        // Then
        verify(videoJpaRepository, times(1)).findById(videoId);
    }

    @Test
    void loadVideoByChannel() {
        // Given
        var videoJpaEntity1 = VideoJpaEntityFixtures.stub("video1");
        var videoJpaEntity2 = VideoJpaEntityFixtures.stub("video2");
        given(videoJpaRepository.findByChannelId(any()))
            .willReturn(List.of(videoJpaEntity1, videoJpaEntity2));

        // When
        for (int i = 0; i < 3; i++) {
            sut.loadVideoByChannel("channelId");
        }

        // Then
        verify(videoJpaRepository, times(1)).findByChannelId("channelId");
    }
}
