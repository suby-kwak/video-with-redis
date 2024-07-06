package com.example.mytv.application.port.out;

import com.example.mytv.domain.Video;
import java.util.List;

public interface LoadVideoPort {
    Video loadVideo(String videoId);
    List<Video> loadVideoByChannel(String channelId);
}
