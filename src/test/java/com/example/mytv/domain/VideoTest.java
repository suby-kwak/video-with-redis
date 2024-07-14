package com.example.mytv.domain;

import static org.assertj.core.api.BDDAssertions.then;

import com.example.mytv.domain.video.VideoFixtures;
import org.junit.jupiter.api.Test;

class VideoTest {
    @Test
    void testBindViewCount() {
        var video = VideoFixtures.stub("videoId");
        video.bindViewCount(100L);

        then(video.getViewCount()).isEqualTo(100L);
    }
}