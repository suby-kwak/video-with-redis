package com.example.mytv.domain.comment;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class Comment {
    private String id;
    private String channelId;
    private String videoId;
    private String text;
    private String parentId;    // 대댓글일때만 원 댓글의 id. 그 외에는 null
    private LocalDateTime publishedAt;
}
