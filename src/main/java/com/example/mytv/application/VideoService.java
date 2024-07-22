package com.example.mytv.application;

import com.example.mytv.adapter.in.api.dto.VideoRequest;
import com.example.mytv.application.port.in.VideoUseCase;
import com.example.mytv.application.port.out.LoadVideoPort;
import com.example.mytv.application.port.out.SaveVideoPort;
import com.example.mytv.domain.Video;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class VideoService implements VideoUseCase {
    private final LoadVideoPort loadVideoPort;
    private final SaveVideoPort saveVideoPort;

    public VideoService(LoadVideoPort loadVideoPort, SaveVideoPort saveVideoPort) {
        this.loadVideoPort = loadVideoPort;
        this.saveVideoPort = saveVideoPort;
    }

    @Override
    public Video getVideo(String videoId) {
        var video = loadVideoPort.loadVideo(videoId);
        var viewCount = loadVideoPort.getViewCount(videoId);
        video.bindViewCount(viewCount);

        return video;
    }

    @Override
    public List<Video> listVideos(String channelId) {
        return loadVideoPort.loadVideoByChannel(channelId).stream()
            .map(video -> {
                var viewCount = loadVideoPort.getViewCount(video.getId());
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
        saveVideoPort.saveVideo(video);
        return video;
    }

    @Override
    public void increaseViewCount(String videoId) {
        saveVideoPort.incrementViewCount(videoId);
    }

    @Override
    public void syncViewCount(String videoId) {
    }
}
