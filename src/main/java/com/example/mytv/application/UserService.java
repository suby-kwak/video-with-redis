package com.example.mytv.application;

import com.example.mytv.application.port.in.UserUserCase;
import com.example.mytv.application.port.out.LoadUserPort;
import com.example.mytv.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserUserCase {
    private final LoadUserPort loadUserPort;
    @Override
    public User getUser(String userId) {
        return loadUserPort.loadUser(userId)
            .orElseThrow();
    }
}
