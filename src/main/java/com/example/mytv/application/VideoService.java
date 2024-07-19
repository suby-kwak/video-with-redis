package com.example.mytv.application;

import com.example.mytv.adapter.out.VideoPersistenceAdapter;
import com.example.mytv.application.port.in.VideoUseCase;
import com.example.mytv.domain.Video;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class VideoService implements VideoUseCase {
    private final VideoPersistenceAdapter videoPersistenceAdapter;

    public VideoService(VideoPersistenceAdapter videoPersistenceAdapter) {
        this.videoPersistenceAdapter = videoPersistenceAdapter;
    }

    @Override
    public Video getVideo(String videoId) {
        var video = videoPersistenceAdapter.loadVideo(videoId);
        var viewCount = videoPersistenceAdapter.getViewCount(videoId);
        video.bindViewCount(viewCount);

        return video;
    }

    @Override
    public List<Video> listVideos(String channelId) {
        return videoPersistenceAdapter.loadVideoByChannel(channelId).stream()
            .map(video -> {
                var viewCount = videoPersistenceAdapter.getViewCount(video.getId());
                video.bindViewCount(viewCount);
                return video;
            })
            .toList();
    }

    @Override
    public void increaseViewCount(String videoId) {
        videoPersistenceAdapter.incrementViewCount(videoId);
    }
}
