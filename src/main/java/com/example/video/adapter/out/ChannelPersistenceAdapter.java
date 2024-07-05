package com.example.video.adapter.out;

import com.example.video.adapter.out.jpa.channel.ChannelJpaEntity;
import com.example.video.adapter.out.jpa.channel.ChannelJpaRepository;
import com.example.video.adapter.out.redis.channel.ChannelRedisHash;
import com.example.video.adapter.out.redis.channel.ChannelRedisRepository;
import com.example.video.application.port.out.LoadChannelPort;
import com.example.video.domain.channel.Channel;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelPersistenceAdapter implements LoadChannelPort {
    private final ChannelJpaRepository channelJpaRepository;
    private final ChannelRedisRepository channelRedisRepository;

    @Override
    public Optional<Channel> loadChannel(String id) {
        return channelRedisRepository.findById(id)
                .map(Channel::from)
                .or(() -> {
                    var optionalEntity = channelJpaRepository.findById(id);
                    optionalEntity.ifPresent(jpaEntity -> channelRedisRepository.save(ChannelRedisHash.fromEntity(jpaEntity)));

                    System.out.println("test" + channelRedisRepository.findById(id));
                    return optionalEntity.map(ChannelJpaEntity::toDomain);
                });
    }
}
