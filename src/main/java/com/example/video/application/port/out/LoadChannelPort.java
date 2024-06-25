package com.example.video.application.port.out;

import com.example.video.domain.channel.Channel;

public interface LoadChannelPort {
    Channel loadChannel(String id);
}
