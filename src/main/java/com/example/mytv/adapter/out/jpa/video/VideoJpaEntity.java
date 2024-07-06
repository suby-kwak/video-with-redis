package com.example.mytv.adapter.out.jpa.video;

import com.example.mytv.adapter.out.jpa.channel.ChannelJpaEntity;
import com.example.mytv.domain.Video;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "video")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class VideoJpaEntity {
    @Id
    private String id;
    private String title;
    private String description;
    private String thumbnail;
    @ManyToOne
    @JoinColumn(name = "channel_id")
    private ChannelJpaEntity channel;
    private LocalDateTime publishedAt;

    public Video toDomain() {
        return Video.builder()
                .id(this.getId())
                .title(this.getTitle())
                .description(this.getDescription())
                .thumbnail(this.getThumbnail())
                .channel(this.getChannel().toDomain())
                .publishedAt(this.getPublishedAt())
                .build();
    }
}
