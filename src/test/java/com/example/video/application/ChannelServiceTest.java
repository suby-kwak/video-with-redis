package com.example.video.application;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.example.video.application.port.out.LoadChannelPort;
import com.example.video.domain.channel.ChannelFixtures;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChannelServiceTest {
    private ChannelService sut;
    @Mock
    private LoadChannelPort loadChannelPort;

    @BeforeEach
    void setUp() {
        sut = new ChannelService(loadChannelPort);
    }

    @Test
    @DisplayName("getChannel")
    void testGetChannel() {
        var id = "channelId";
        given(loadChannelPort.loadChannel(any()))
            .willReturn(Optional.of(ChannelFixtures.stub(id)));

        var result = sut.getChannel(id);

        then(result)
            .isNotNull()
            .hasFieldOrPropertyWithValue("id", id);
    }
}