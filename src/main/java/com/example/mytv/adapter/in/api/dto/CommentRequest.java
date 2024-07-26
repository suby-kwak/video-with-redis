package com.example.mytv.adapter.in.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CommentRequest {
    private String channelId;
    private String videoId;
    private String text;
}
