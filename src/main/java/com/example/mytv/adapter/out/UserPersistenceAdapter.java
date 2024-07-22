package com.example.mytv.adapter.out;

import com.example.mytv.adapter.out.jpa.user.UserJpaEntity;
import com.example.mytv.adapter.out.jpa.user.UserJpaRepository;
import com.example.mytv.application.port.out.LoadUserPort;
import com.example.mytv.domain.User;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceAdapter implements LoadUserPort {
    private final UserJpaRepository userJpaRepository;

    public UserPersistenceAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Optional<User> loadUser(String userId) {
        return userJpaRepository.findById(userId)
                .map(UserJpaEntity::toDomain);
    }
}
