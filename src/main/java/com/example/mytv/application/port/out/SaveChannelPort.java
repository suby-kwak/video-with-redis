package com.example.mytv.application.port.out;

import com.example.mytv.domain.channel.Channel;
import com.example.mytv.domain.channel.ChannelSnippet;

public interface SaveChannelPort {
    void createChannel(Channel channel);

    void updateChannel(String channelId, Channel channel);
}
