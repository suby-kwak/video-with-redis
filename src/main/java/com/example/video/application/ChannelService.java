package com.example.video.application;

import com.example.video.application.port.in.GetChannelUserCase;
import com.example.video.application.port.out.LoadChannelPort;
import com.example.video.domain.channel.Channel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelService implements GetChannelUserCase {
    private final LoadChannelPort loadChannelPort;

    @Override
    public Channel getChannel(String id) {
        return loadChannelPort.loadChannel(id).get();
    }
}
