package com.example.video.adapter.out;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.example.video.adapter.out.jpa.channel.ChannelJpaEntityFixtures;
import com.example.video.adapter.out.jpa.channel.ChannelJpaRepository;
import com.example.video.adapter.out.redis.channel.ChannelRedisHash;
import com.example.video.adapter.out.redis.channel.ChannelRedisRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChannelPersistenceAdapterTest {
    private ChannelPersistenceAdapter sut;
    @Mock
    private ChannelJpaRepository channelJpaRepository;
    @Mock
    private ChannelRedisRepository channelRedisRepository;

    @BeforeEach
    void setUp() {
        sut = new ChannelPersistenceAdapter(channelJpaRepository, channelRedisRepository);
    }

    @Nested
    @DisplayName("loadChannel")
    class LoadChannelTest {
        @Test
        @DisplayName("Redis 에서 Channel 을 찾을 수 있으면 RedisHash 에서 Channel 반환")
        void existRedis_returnRedisRepository() {
            var id = "channelId";
            given(channelRedisRepository.findById(any()))
                .willReturn(Optional.of(new ChannelRedisHash(id)));

            var result = sut.loadChannel(id);

            then(result)
                .isPresent()
                .hasValueSatisfying(channel -> {
                    then(channel)
                        .hasFieldOrPropertyWithValue("id", id);
                });

        }

        @Test
        @DisplayName("Redis 에서 Channel 을 찾을 수 없으면 Jpa 에서 Channel 반환")
        void notExistRedis_returnJpaRepository() {
            var id = "channelId";
            given(channelRedisRepository.findById(any()))
                .willReturn(Optional.empty());
            given(channelJpaRepository.findById(any()))
                .willReturn(Optional.of(ChannelJpaEntityFixtures.stub(id)));

            var result = sut.loadChannel(id);

            then(result)
                .isPresent()
                .hasValueSatisfying(channel -> {
                    then(channel)
                        .hasFieldOrPropertyWithValue("id", id);
                });
        }

        @Test
        @DisplayName("Redis, Jpa 에서 Channel 을 찾을 수 없으면 empty Optional 반환")
        void notExist_returnEmpty() {
            var id = "channelId";
            given(channelRedisRepository.findById(any()))
                .willReturn(Optional.empty());
            given(channelJpaRepository.findById(any()))
                .willReturn(Optional.empty());

            var result = sut.loadChannel(id);

            then(result)
                .isNotPresent();
        }
    }
}