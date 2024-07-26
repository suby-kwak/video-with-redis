package com.example.mytv.domain.user;

public class UserFixtures {
    public static User stub() {
        return User.builder()
            .id("userId")
            .name("name")
            .build();
    }

    public static User stub(String id) {
        return User.builder()
            .id(id)
            .name("name" + id)
            .build();
    }
}
