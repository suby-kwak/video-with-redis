package com.example.mytv.adapter.in.api;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.mytv.application.port.in.ChannelUseCase;
import com.example.mytv.domain.channel.ChannelFixtures;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ChannelApiController.class)
class ChannelApiControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChannelUseCase channelUseCase;

    @Test
    @DisplayName("POST /api/v1/channels")
    void createChannel() throws Exception {
        // Given
        var channelRequest = new ChannelRequest(new ChannelSnippetRequest("title", "description", "https://example.com/thumbnail"), "userId");
        given(channelUseCase.createChannel(any())).willReturn(ChannelFixtures.stub("channelId"));

        // When, Then
        mockMvc
            .perform(
                post("/api/v1/channels")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(channelRequest))
            )
            .andExpectAll(
                status().isOk()
            );

        verify(channelUseCase).createChannel(
            argThat(param -> {
                    then(param.getSnippet())
                        .hasFieldOrPropertyWithValue("title", "title")
                        .hasFieldOrPropertyWithValue("description", "description")
                        .hasFieldOrPropertyWithValue("thumbnailUrl", "https://example.com/thumbnail");
                    then(param.getContentOwnerId())
                        .isEqualTo("userId");
                    return true;
                })
        );
    }
}