package com.example.video.domain.user;

import com.example.video.domain.User;

public class UserFixtures {
    public static User stub() {
        return User.builder()
            .build();
    }

    public static User channelOwner() {
        return User.builder()
            .build();
    }
}
