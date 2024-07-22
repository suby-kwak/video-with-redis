package com.example.mytv.adapter.out;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.mytv.adapter.out.jpa.channel.ChannelJpaRepository;
import com.example.mytv.adapter.out.redis.channel.ChannelRedisHash;
import com.example.mytv.adapter.out.redis.channel.ChannelRedisRepository;
import com.example.mytv.config.TestRedisConfig;
import com.example.mytv.domain.channel.Channel;
import com.example.mytv.domain.channel.ChannelFixtures;
import com.example.mytv.domain.channel.ChannelSnippet;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = TestRedisConfig.class)
@Transactional
@Sql("/com/example/mytv/adapter/out/VideoPersistenceAdapterIntTest.sql")
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
            for (int i = 0; i < 3; i++) {
                sut.loadChannel("channel1");
            }

            verify(channelJpaRepository, times(1)).findById("channel1");
            verify(channelRedisRepository, times(3)).findById("channel1");
        }

        @Test
        @DisplayName("Redis Cache에서 찾을 수 있으면 Jpa 호출하지 않음")
        void cacheHitTest() {
            when(channelRedisRepository.findById("channel1")).thenReturn(Optional.of(ChannelRedisHash.from(ChannelFixtures.stub("channelId"))));

            for (int i = 0; i < 3; i++) {
                sut.loadChannel("channel1");
            }

            verify(channelJpaRepository, never()).findById("channel1");
        }
    }

    @Nested
    @DisplayName("save Channel")
    class SaveChannel {
        @Test
        void testUpdateAndLoadChannel() {
            // given
            var channel = sut.loadChannel("channel1").get();
            System.out.println(channel);
            var updatedChannel = Channel.builder()
                .id(channel.getId())
                .snippet(ChannelSnippet.builder().title("new title").description("new description").thumbnailUrl("https://example.com/newimage.jpg").build())
                .statistics(channel.getStatistics())
                .contentOwnerId(channel.getContentOwnerId())
                .build();

            // When
            sut.saveChannel(updatedChannel);

            // Then
            var result = sut.loadChannel("channel1");
            then(result)
                .isPresent();
            then(result.get())
                .hasFieldOrPropertyWithValue("id", channel.getId())
                .hasFieldOrPropertyWithValue("snippet.title", "new title")
                .hasFieldOrPropertyWithValue("snippet.description", "new description")
                .hasFieldOrPropertyWithValue("snippet.thumbnailUrl", "https://example.com/newimage.jpg");
            System.out.println(result.get());
        }
    }
}
