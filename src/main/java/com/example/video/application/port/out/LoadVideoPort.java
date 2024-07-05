package com.example.video.application.port.out;

import com.example.video.domain.Video;
import java.util.List;

public interface LoadVideoPort {
    Video loadVideo(String videoId);
    List<Video> loadVideoByChannel(String channelId);
}
