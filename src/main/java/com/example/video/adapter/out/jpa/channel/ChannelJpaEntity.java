package com.example.video.adapter.out.jpa.channel;

import com.example.video.domain.channel.Channel;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "channel")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChannelJpaEntity {
    @Id
    private String id;

    @Embedded
    private ChannelSnippetJpaEntity channelSnippet;

    @Embedded
    private ChannelStatisticsJpaEntity statistics;

    public Channel toDomain() {
        return Channel.builder()
                .id(this.getId())
                .build();
    }
}
