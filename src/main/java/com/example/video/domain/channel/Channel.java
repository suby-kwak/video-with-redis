package com.example.video.domain.channel;

import com.example.video.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class Channel {
    private String id;
    private ChannelSnippet snippet;
    private ChannelStatistics statistics;
    private User contentOwner;
}
