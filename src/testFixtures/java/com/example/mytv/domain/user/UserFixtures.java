package com.example.mytv.domain.user;

public class UserFixtures {
    public static User stub() {
        return User.builder()
            .id("userId")
            .build();
    }

    public static User channelOwner() {
        return User.builder()
            .id("userId")
            .build();
    }
}
