package com.example.video.domain;

import com.example.video.domain.channel.Channel;
import java.io.Serializable;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class Video implements Serializable {
    private String id;
    private String title;
    private String description;
    private String thumbnail;
    private Channel channel;
    private ZonedDateTime publishedAt;
}
