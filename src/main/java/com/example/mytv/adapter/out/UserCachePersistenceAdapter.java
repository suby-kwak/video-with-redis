package com.example.mytv.adapter.out;

import com.example.mytv.adapter.out.jpa.user.UserJpaEntity;
import com.example.mytv.adapter.out.jpa.user.UserJpaRepository;
import com.example.mytv.adapter.out.redis.user.UserRedisHash;
import com.example.mytv.adapter.out.redis.user.UserRedisRepository;
import com.example.mytv.application.port.out.LoadUserPort;
import com.example.mytv.domain.user.User;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserCachePersistenceAdapter implements LoadUserPort {
    private final UserJpaRepository userJpaRepository;
    private final UserRedisRepository userRedisRepository;

    public UserCachePersistenceAdapter(UserJpaRepository userJpaRepository, UserRedisRepository userRedisRepository) {
        this.userJpaRepository = userJpaRepository;
        this.userRedisRepository = userRedisRepository;
    }

    @Override
    public Optional<User> loadUser(String userId) {
        return userRedisRepository.findById(userId)
            .map(UserRedisHash::toDomain)
            .or(() -> {
                var optionalEntity = userJpaRepository.findById(userId);
                optionalEntity.ifPresent(userJpaEntity -> userRedisRepository.save(UserRedisHash.from(userJpaEntity.toDomain())));
                return optionalEntity.map(UserJpaEntity::toDomain);
            });
    }
}
