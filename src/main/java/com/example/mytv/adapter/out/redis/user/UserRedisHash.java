package com.example.mytv.adapter.out.redis.user;

import com.example.mytv.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("user")
@AllArgsConstructor
@Getter
public class UserRedisHash {
    private String id;
    private String name;

    public static UserRedisHash from(User user) {
        return new UserRedisHash(user.getId(), user.getName());
    }

    public User toDomain() {
        return User.builder()
            .id(this.getId())
            .name(this.getName())
            .build();
    }
}
