package com.example.mytv.application.port.out;

import com.example.mytv.domain.user.User;
import java.util.Optional;

public interface LoadUserPort {
    Optional<User> loadUser(String userId);
}
