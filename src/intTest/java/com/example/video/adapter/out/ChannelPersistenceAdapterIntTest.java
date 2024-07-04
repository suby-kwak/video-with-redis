package com.example.video.adapter.out;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.video.adapter.out.jpa.channel.ChannelJpaEntity;
import com.example.video.adapter.out.jpa.channel.ChannelJpaEntityFixtures;
import com.example.video.adapter.out.jpa.channel.ChannelJpaRepository;
import com.example.video.adapter.out.redis.ChannelRedisHash;
import com.example.video.adapter.out.redis.ChannelRedisRepository;
import com.example.video.config.TestRedisConfig;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = TestRedisConfig.class)
@Transactional
@ExtendWith(MockitoExtension.class)
public class ChannelPersistenceAdapterIntTest {
    @Autowired
    private ChannelPersistenceAdapter sut;

    @SpyBean
    private ChannelJpaRepository channelJpaRepository;
    @SpyBean
    private ChannelRedisRepository channelRedisRepository;

    @Nested
    @DisplayName("loadChannel")
    class LoadChannelTest {
        @Test
        @DisplayName("Redis cache에서 찾을 수 없으면 Jpa 에서 찾음")
        void cacheMissAndJpaHitTest() {
            when(channelJpaRepository.findById("channelId")).thenReturn(Optional.of(ChannelJpaEntityFixtures.stub("channelId")));

            for (int i = 0; i < 3; i++) {
                sut.loadChannel("channelId");
            }

            verify(channelJpaRepository).findById("channelId");
        }

        @Test
        @DisplayName("Redis Cache에서 찾을 수 있으면 Jpa 호출하지 않음")
        void cacheHitTest() {
            when(channelRedisRepository.findById("channelId")).thenReturn(Optional.of(new ChannelRedisHash("channelId")));

            for (int i = 0; i < 3; i++) {
                sut.loadChannel("channelId");
            }

            verify(channelJpaRepository, never()).findById("channelId");
        }
    }

}
