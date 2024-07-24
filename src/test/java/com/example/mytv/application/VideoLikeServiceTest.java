package com.example.mytv.application;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.example.mytv.application.port.out.VideoLikePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VideoLikeServiceTest {
    private VideoLikeService sut;

    private final VideoLikePort videoLikePort = mock(VideoLikePort.class);

    @BeforeEach
    void setUp() {
        sut = new VideoLikeService(videoLikePort);
    }

    @Test
    @DisplayName("user가 video 에 대해 좋아요를 하면 user-video like 추가")
    void testLikeVideo() {
        // given
        var videoId = "videoId";
        var userId = "userId";
        given(videoLikePort.addVideoLike(any(), any())).willReturn(3L);
        // when
        var result = sut.likeVideo(videoId, userId);
        // then
        then(result).isEqualTo(3L);
    }

    @Test
    @DisplayName("user가 video 에 대해 좋아요를 취소하면 user-video like 제거")
    void testUnlikeVideo() {
        // given
        var videoId = "videoId";
        var userId = "userId";
        given(videoLikePort.removeVideoLike(any(), any())).willReturn(4L);
        // when
        var result = sut.unlikeVideo(videoId, userId);
        // then
        then(result).isEqualTo(4L);
    }
}