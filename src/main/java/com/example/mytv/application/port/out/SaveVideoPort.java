package com.example.mytv.application.port.out;

import com.example.mytv.domain.Video;

public interface SaveVideoPort {
    void saveVideo(Video video);
    void incrementViewCount(String videoId);
}
