package com.example.mytv.adapter.out.jpa.channel;

import com.example.mytv.domain.channel.ChannelStatistics;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ChannelStatisticsJpaEntity {
    private int viewCount;
    private int videoCount;
    private int subscriberCount;
    private int commentCount;

    public static ChannelStatisticsJpaEntity from(ChannelStatistics statistics) {
        return new ChannelStatisticsJpaEntity(statistics.getViewCount(), statistics.getVideoCount(), statistics.getSubscriberCount(), statistics.getCommentCount());
    }

    public ChannelStatistics toDomain() {
        return ChannelStatistics.builder()
            .viewCount(this.getViewCount())
            .videoCount(this.getVideoCount())
            .subscriberCount(this.getSubscriberCount())
            .commentCount(this.getCommentCount())
            .build();
    }
}
