package com.example.video.adapter.out.jpa;

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
}
