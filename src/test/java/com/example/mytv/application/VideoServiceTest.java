package com.example.mytv.application;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.example.mytv.adapter.in.dto.VideoRequestFixtures;
import com.example.mytv.adapter.out.VideoPersistenceAdapter;
import com.example.mytv.domain.video.VideoFixtures;
import java.util.stream.LongStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VideoServiceTest {
    private VideoService sut;

    private final VideoPersistenceAdapter videoPersistenceAdapter = mock(VideoPersistenceAdapter.class);

    @BeforeEach
    void setUp() {
        sut = new VideoService(videoPersistenceAdapter);
    }

    @Test
    @DisplayName("videoId로 조회시 Video 반환")
    void testLoadVideo() {
        // Given
        var videoId = "videoId";
        given(videoPersistenceAdapter.loadVideo(any())).willReturn(VideoFixtures.stub(videoId));
        given(videoPersistenceAdapter.getViewCount(any())).willReturn(150L);
        // When
        var result = sut.getVideo(videoId);
        // Then
        then(result)
            .isNotNull()
            .hasFieldOrPropertyWithValue("id", videoId)
            .hasFieldOrPropertyWithValue("viewCount", 150L);
    }

    @Test
    @DisplayName("channelId로 조회시 Video 목록 반환")
    void testListVideos() {
        // Given
        var channelId = "channelId";
        var list = LongStream.range(1L, 4L)
            .mapToObj(i -> VideoFixtures.stub("videoId" + i))
            .toList();
        given(videoPersistenceAdapter.loadVideoByChannel(any())).willReturn(list);
        given(videoPersistenceAdapter.getViewCount(any())).willReturn(100L, 150L, 200L);
        // When
        var result = sut.listVideos(channelId);
        // Then
        then(result)
            .hasSize(3)
            .extracting("channelId").containsOnly(channelId);
        then(result)
            .extracting("viewCount").contains(100L, 150L, 200L);
    }

    @Test
    void testCreateVideo() {
        var videoRequest = VideoRequestFixtures.stub();
        willDoNothing().given(videoPersistenceAdapter).createVideo(any());

        var result = sut.createVideo(videoRequest);

        // Then
        then(result)
            .isNotNull()
            .hasFieldOrProperty("id");
        verify(videoPersistenceAdapter).createVideo(any());
    }

    @Test
    void testIncrementViewCount() {
        given(videoPersistenceAdapter.loadVideo(any())).willReturn(VideoFixtures.stub("videoId"));

        sut.increaseViewCount("videoId");

        verify(videoPersistenceAdapter).incrementViewCount("videoId");
    }
}