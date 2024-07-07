package com.example.mytv.domain.channel;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ChannelSnippet {
    private String title;
    private String description;
    private LocalDateTime publishedAt;
    private String thumbnailUrl;
}
