package com.example.mytv.adapter.out;

import com.example.mytv.application.port.out.LoadUserPort;
import com.example.mytv.domain.User;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceAdapter implements LoadUserPort {
    @Override
    public Optional<User> loadUser(String userId) {
        return Optional.empty();
    }
}
