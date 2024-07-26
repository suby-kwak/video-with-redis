package com.example.mytv.adapter.out;

import static com.example.mytv.util.CacheNames.SUBSCRIBE_CHANNEL;

import com.example.mytv.adapter.out.jpa.channel.ChannelJpaEntity;
import com.example.mytv.adapter.out.jpa.subscribe.SubscribeJpaEntity;
import com.example.mytv.adapter.out.jpa.subscribe.SubscribeJpaRepository;
import com.example.mytv.adapter.out.jpa.user.UserJpaEntity;
import com.example.mytv.application.port.out.SubscribePort;
import com.example.mytv.domain.user.User;
import com.example.mytv.domain.channel.Channel;
import java.util.List;
import java.util.UUID;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class SubscribePersistenceAdapter implements SubscribePort {
    private final SubscribeJpaRepository subscribeJpaRepository;

    public SubscribePersistenceAdapter(SubscribeJpaRepository subscribeJpaRepository) {
        this.subscribeJpaRepository = subscribeJpaRepository;
    }

    @Override
    @CacheEvict(cacheNames = SUBSCRIBE_CHANNEL, key = "#user.id")
    public String insertSubscribeChannel(Channel channel, User user) {
        var subscribeJpaEntity = new SubscribeJpaEntity(
            UUID.randomUUID().toString(),
            ChannelJpaEntity.from(channel),
            UserJpaEntity.from(user)
        );
        subscribeJpaRepository.save(subscribeJpaEntity);

        return subscribeJpaEntity.getId();
    }

    @Override
    @Cacheable(cacheNames = SUBSCRIBE_CHANNEL, key = "#userId")
    public List<Channel> listSubscribeChannel(String userId) {
        return subscribeJpaRepository.findAllByUserId(userId)
            .stream()
            .map(SubscribeJpaEntity::getChannel)
            .map(ChannelJpaEntity::toDomain)
            .toList();
    }
}
