package com.example.mytv.application;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.example.mytv.adapter.in.dto.VideoRequestFixtures;
import com.example.mytv.application.port.out.LoadChannelPort;
import com.example.mytv.application.port.out.LoadVideoPort;
import com.example.mytv.application.port.out.SaveChannelPort;
import com.example.mytv.application.port.out.SaveVideoPort;
import com.example.mytv.domain.channel.ChannelFixtures;
import com.example.mytv.domain.video.VideoFixtures;
import java.util.Optional;
import java.util.stream.LongStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VideoServiceTest {
    private VideoService sut;

    private final LoadVideoPort loadVideoPort = mock(LoadVideoPort.class);
    private final SaveVideoPort saveVideoPort = mock(SaveVideoPort.class);
    private final LoadChannelPort loadChannelPort = mock(LoadChannelPort.class);
    private final SaveChannelPort saveChannelPort = mock(SaveChannelPort.class);

    @BeforeEach
    void setUp() {
        sut = new VideoService(loadVideoPort, saveVideoPort, loadChannelPort, saveChannelPort);
    }

    @Test
    @DisplayName("videoId로 조회시 Video 반환")
    void testLoadVideo() {
        // Given
        var videoId = "videoId";
        given(loadVideoPort.loadVideo(any())).willReturn(VideoFixtures.stub(videoId));
        given(loadVideoPort.getViewCount(any())).willReturn(150L);
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
        given(loadVideoPort.loadVideoByChannel(any())).willReturn(list);
        given(loadVideoPort.getViewCount(any())).willReturn(100L, 150L, 200L);
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
        willDoNothing().given(saveVideoPort).saveVideo(any());
        given(loadChannelPort.loadChannel(any())).willReturn(Optional.of(ChannelFixtures.stub(videoRequest.getChannelId())));

        var result = sut.createVideo(videoRequest);

        // Then
        then(result)
            .isNotNull()
            .hasFieldOrProperty("id");
        verify(saveVideoPort).saveVideo(any());
        verify(saveChannelPort).saveChannel(any());
    }

    @Test
    void testIncrementViewCount() {
        sut.increaseViewCount("videoId");

        verify(saveVideoPort).incrementViewCount("videoId");
    }
}