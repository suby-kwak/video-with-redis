package com.example.mytv.application;

import com.example.mytv.adapter.in.api.dto.VideoRequest;
import com.example.mytv.adapter.out.VideoPersistenceAdapter;
import com.example.mytv.application.port.in.VideoUseCase;
import com.example.mytv.domain.Video;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
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
    public Video createVideo(VideoRequest videoRequest) {
        var video = Video.builder()
                .id(UUID.randomUUID().toString())
                .title(videoRequest.getTitle())
                .description(videoRequest.getDescription())
                .thumbnailUrl(videoRequest.getThumbnailUrl())
                .channelId(videoRequest.getChannelId())
                .publishedAt(LocalDateTime.now())
                .build();
        videoPersistenceAdapter.createVideo(video);
        return video;
    }

    @Override
    public void increaseViewCount(String videoId) {
        videoPersistenceAdapter.incrementViewCount(videoId);
    }
}
