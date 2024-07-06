package com.example.mytv.adapter.out.jpa.user;

import com.example.mytv.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserJpaEntity {
    @Id
    private String id;

    private String name;

    public static UserJpaEntity from(User user) {
        return new UserJpaEntity(user.getId(), user.getName());
    }

    public User toDomain() {
        return User.builder()
            .id(this.getId())
            .name(this.getName())
            .build();
    }
}
