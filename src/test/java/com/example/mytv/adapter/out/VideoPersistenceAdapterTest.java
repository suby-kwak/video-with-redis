package com.example.mytv.adapter.out;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.example.mytv.adapter.out.jpa.video.VideoJpaEntity;
import com.example.mytv.adapter.out.jpa.video.VideoJpaEntityFixtures;
import com.example.mytv.adapter.out.jpa.video.VideoJpaRepository;
import com.example.mytv.domain.video.VideoFixtures;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@ExtendWith(MockitoExtension.class)
class VideoPersistenceAdapterTest {
    private VideoPersistenceAdapter sut;

    private final VideoJpaRepository videoJpaRepository = mock(VideoJpaRepository.class);
    private final RedisTemplate<String, Long> redisTemplate = mock(RedisTemplate.class, Mockito.RETURNS_DEEP_STUBS);
    private final ValueOperations<String, Long> valueOperations = mock(ValueOperations.class);

    @BeforeEach
    void setUp() {
        sut = new VideoPersistenceAdapter(videoJpaRepository, redisTemplate);
    }

    @Nested
    class LoadVideo {
        @Test
        void existVideo_then_returnVideo() {
            // Given
            var videoJpaEntity = VideoJpaEntityFixtures.stub("video1");
            given(videoJpaRepository.findById(any()))
                .willReturn(Optional.of(videoJpaEntity));

            // When
            var result = sut.loadVideo("video1");

            // Then
            then(result)
                .extracting("id")
                .isEqualTo("video1");
        }

        @Test
        void notExistVideo_then_throwException() {
            // Given
            given(videoJpaRepository.findById(any()))
                .willReturn(Optional.empty());

            // When
            var result = thenThrownBy(() -> sut.loadVideo("video1"));

            // Then
            result.isInstanceOf(NoSuchElementException.class);
        }
    }

    @Test
    void testLoadVideoByChannel() {
        // Given
        var videoJpaEntity1 = VideoJpaEntityFixtures.stub("video1");
        var videoJpaEntity2 = VideoJpaEntityFixtures.stub("video2");
        given(videoJpaRepository.findByChannelId(any()))
            .willReturn(List.of(videoJpaEntity1, videoJpaEntity2));

        // When
        var result = sut.loadVideoByChannel("channelId");

        // Then
        then(result)
            .hasSize(2)
            .extracting("id")
            .containsExactly("video1", "video2");
    }

    @Test
    void testCreateVideo() {
        // Given
        var video = VideoFixtures.stub("videoId");
        ArgumentCaptor<VideoJpaEntity> argumentCaptor = ArgumentCaptor.forClass(VideoJpaEntity.class);

        // When
        sut.saveVideo(video);

        // Then
        verify(videoJpaRepository).save(argumentCaptor.capture());
        then(argumentCaptor.getValue())
            .hasFieldOrPropertyWithValue("title", video.getTitle())
            .hasFieldOrPropertyWithValue("description", video.getDescription())
            .hasFieldOrPropertyWithValue("thumbnailUrl", video.getThumbnailUrl())
            .hasFieldOrPropertyWithValue("channelId", video.getChannelId());
    }

    @Test
    void testIncrementViewCount() {
        given(redisTemplate.opsForValue()).willReturn(valueOperations);
        given(valueOperations.increment(any())).willReturn(5L);

        sut.incrementViewCount("video1");

        verify(valueOperations).increment("video:view-count:video1");
    }

    @Test
    void testGetViewCount() {
        given(redisTemplate.opsForValue().get(any())).willReturn(10L);

        var result = sut.getViewCount("video1");

        then(result).isEqualTo(10L);
    }
}