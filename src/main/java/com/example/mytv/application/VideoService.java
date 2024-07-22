package com.example.mytv.application;

import com.example.mytv.adapter.in.api.dto.VideoRequest;
import com.example.mytv.application.port.in.VideoUseCase;
import com.example.mytv.application.port.out.LoadChannelPort;
import com.example.mytv.application.port.out.LoadVideoPort;
import com.example.mytv.application.port.out.SaveChannelPort;
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
    private final LoadChannelPort loadChannelPort;
    private final SaveChannelPort saveChannelPort;

    public VideoService(LoadVideoPort loadVideoPort, SaveVideoPort saveVideoPort, LoadChannelPort loadChannelPort, SaveChannelPort saveChannelPort) {
        this.loadVideoPort = loadVideoPort;
        this.saveVideoPort = saveVideoPort;
        this.loadChannelPort = loadChannelPort;
        this.saveChannelPort = saveChannelPort;
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

        var channel = loadChannelPort.loadChannel(videoRequest.getChannelId()).get();
        channel.getStatistics().updateVideoCount(channel.getStatistics().getVideoCount() + 1);
        saveChannelPort.saveChannel(channel);

        return video;
    }

    @Override
    public void increaseViewCount(String videoId) {
        saveVideoPort.incrementViewCount(videoId);
    }
}
