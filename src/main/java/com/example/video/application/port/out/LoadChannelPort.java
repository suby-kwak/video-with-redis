package com.example.video.application.port.out;

import com.example.video.domain.channel.Channel;
import java.util.Optional;

public interface LoadChannelPort {
    Optional<Channel> loadChannel(String id);
}
