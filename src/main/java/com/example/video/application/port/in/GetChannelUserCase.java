package com.example.video.application.port.in;

import com.example.video.domain.channel.Channel;

public interface GetChannelUserCase {
    Channel getChannel(String id);
}
