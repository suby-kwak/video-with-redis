package com.example.mytv.adapter.out.redis.video;

import com.example.mytv.adapter.out.jpa.video.VideoJpaEntity;
import com.example.mytv.adapter.out.redis.channel.ChannelRedisHash;
import com.example.mytv.domain.Video;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("video")
@AllArgsConstructor
@Getter
public class VideoRedisHash implements Serializable {
    private String id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private ChannelRedisHash channel;
    private LocalDateTime publishedAt;

    public static VideoRedisHash fromJpaEntity(VideoJpaEntity videoJpaEntity) {
        return new VideoRedisHash(
                videoJpaEntity.getId(),
                videoJpaEntity.getTitle(),
                videoJpaEntity.getDescription(),
                videoJpaEntity.getThumbnailUrl(),
                ChannelRedisHash.from(videoJpaEntity.getChannel().toDomain()),
                videoJpaEntity.getPublishedAt()
        );
    }

    public Video toDomain() {
        return Video.builder()
                .id(this.getId())
                .title(this.getTitle())
                .description(this.getDescription())
                .thumbnailUrl(this.getThumbnailUrl())
                .channel(this.getChannel().toDomain())
                .publishedAt(this.getPublishedAt())
                .build();
    }
}
