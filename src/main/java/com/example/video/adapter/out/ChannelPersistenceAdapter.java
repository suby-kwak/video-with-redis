package com.example.video.adapter.out;

import com.example.video.adapter.out.jpa.ChannelJpaRepository;
import com.example.video.adapter.out.redis.ChannelRedisRepository;
import com.example.video.application.port.out.LoadChannelPort;
import com.example.video.domain.channel.Channel;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelPersistenceAdapter implements LoadChannelPort {
    private final ChannelJpaRepository channelJpaRepository;
    private final ChannelRedisRepository channelRedisRepository;

    @Override
    public Optional<Channel> loadChannel(String id) {
        return channelRedisRepository.findById(id)
                .map(Channel::from)
                .or(() -> channelJpaRepository.findById(id)
                    .map(Channel::from));
    }
}
