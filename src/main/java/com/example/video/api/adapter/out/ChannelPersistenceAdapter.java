package com.example.video.api.adapter.out;

import com.example.video.application.port.out.LoadChannelPort;
import com.example.video.domain.channel.Channel;
import org.springframework.stereotype.Service;

@Service
public class ChannelPersistenceAdapter implements LoadChannelPort {
    @Override
    public Channel loadChannel(String id) {
        // TODO: Implement this method
        return null;
    }
}
