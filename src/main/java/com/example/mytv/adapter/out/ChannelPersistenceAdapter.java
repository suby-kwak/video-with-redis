package com.example.mytv.adapter.out;

import com.example.mytv.adapter.out.jpa.channel.ChannelJpaEntity;
import com.example.mytv.adapter.out.jpa.channel.ChannelJpaRepository;
import com.example.mytv.adapter.out.redis.channel.ChannelRedisHash;
import com.example.mytv.adapter.out.redis.channel.ChannelRedisRepository;
import com.example.mytv.application.port.out.LoadChannelPort;
import com.example.mytv.application.port.out.SaveChannelPort;
import com.example.mytv.domain.channel.Channel;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ChannelPersistenceAdapter implements LoadChannelPort, SaveChannelPort {
    private final ChannelJpaRepository channelJpaRepository;
    private final ChannelRedisRepository channelRedisRepository;

    public ChannelPersistenceAdapter(ChannelJpaRepository channelJpaRepository, ChannelRedisRepository channelRedisRepository) {
        this.channelJpaRepository = channelJpaRepository;
        this.channelRedisRepository = channelRedisRepository;
    }

    @Override
    public void createChannel(Channel channel) {
        channelJpaRepository.save(ChannelJpaEntity.from(channel));
        channelRedisRepository.save(ChannelRedisHash.from(channel));
    }

    @Override
    public Optional<Channel> loadChannel(String id) {
        return channelRedisRepository.findById(id)
                .map(Channel::from)
                .or(() -> {
                    var optionalEntity = channelJpaRepository.findById(id);
                    optionalEntity.ifPresent(jpaEntity -> channelRedisRepository.save(ChannelRedisHash.from(jpaEntity.toDomain())));

                    return optionalEntity.map(ChannelJpaEntity::toDomain);
                });
    }
}
