package com.example.mytv.adapter.out;

import com.example.mytv.adapter.out.jpa.user.UserJpaEntity;
import com.example.mytv.adapter.out.jpa.user.UserJpaRepository;
import com.example.mytv.application.port.out.SaveUserPort;
import com.example.mytv.domain.user.User;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component("UserCreateAdapter")
public class UserCreateAdapter implements SaveUserPort {

    private final StringRedisTemplate stringRedisTemplate;
    private final UserJpaRepository userJpaRepository;

    public UserCreateAdapter(StringRedisTemplate stringRedisTemplate,
        UserJpaRepository userJpaRepository) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    @Transactional
    public String saveUser(User user) {
        userJpaRepository.save(UserJpaEntity.from(user));
        String authkey = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set("user-session:" + authkey, user.getId());
        return authkey;
    }
}
