package com.example.mytv.application;

import com.example.mytv.adapter.out.UserPersistenceAdapter;
import com.example.mytv.application.port.in.UserUserCase;
import com.example.mytv.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserUserCase {
    private final UserPersistenceAdapter userPersistenceAdapter;
    @Override
    public User getUser(String userId) {
        return userPersistenceAdapter.loadUser(userId)
            .orElseThrow();
    }
}
