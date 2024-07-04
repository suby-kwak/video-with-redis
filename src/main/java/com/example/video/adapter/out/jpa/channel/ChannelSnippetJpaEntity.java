package com.example.video.adapter.out.jpa.channel;

import java.time.ZonedDateTime;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChannelSnippetJpaEntity {
    private String title;
    private String description;
    private ZonedDateTime publishedAt;
    private String thumbnailUrl;
}
