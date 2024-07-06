package com.example.mytv.adapter.in.api;

import com.example.mytv.application.port.in.ChannelUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/channels")
@RequiredArgsConstructor
public class ChannelApiController {
    private final ChannelUseCase channelUseCase;

    @PostMapping
    public void createChannel(@RequestBody ChannelRequest channelRequest) {
        channelUseCase.createChannel(channelRequest);
    }
}
