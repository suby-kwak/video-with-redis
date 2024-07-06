package com.example.mytv.adapter.out.jpa.channel;

import com.example.mytv.adapter.out.jpa.user.UserJpaEntity;
import com.example.mytv.domain.channel.Channel;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "content_owner_id")
    private UserJpaEntity contentOwner;

    public Channel toDomain() {
        return Channel.builder()
            .id(this.getId())
            .snippet(this.getChannelSnippet().toDomain())
            .statistics(this.getStatistics().toDomain())
            .contentOwner(this.getContentOwner().toDomain())
            .build();
    }

    public static ChannelJpaEntity from(Channel channel) {
        return new ChannelJpaEntity(
            channel.getId(),
            ChannelSnippetJpaEntity.from(channel.getSnippet()),
            ChannelStatisticsJpaEntity.from(channel.getStatistics()),
            UserJpaEntity.from(channel.getContentOwner())
        );
    }
}
