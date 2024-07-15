package com.example.mytv.application.port.in;

import com.example.mytv.domain.Video;
import java.util.List;

public interface VideoUseCase {
    Video getVideo(String videoId);

    List<Video> listVideos(String channelId);

    void increaseViewCount(String videoId);
}
