package com.example.mytv.application;

import com.example.mytv.application.port.in.UserUserCase;
import com.example.mytv.application.port.out.LoadUserPort;
import com.example.mytv.application.port.out.SaveUserPort;
import com.example.mytv.domain.user.User;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserUserCase {
    private final LoadUserPort loadUserPort;
    private final SaveUserPort saveUserPort;

    public UserService(LoadUserPort loadUserPort, SaveUserPort saveUserPort) {
        this.loadUserPort = loadUserPort;
        this.saveUserPort = saveUserPort;
    }

    @Override
    public User getUser(String userId) {
        if (userId == null) {
            return null;
        }
        return loadUserPort.loadUser(userId)
            .orElse(null);
    }

    @Override
    public String createUser(User request) {
        var user = User.builder()
            .id(request.getId())
            .name(request.getName())
            .profileImageUrl(request.getProfileImageUrl())
            .build();

        return saveUserPort.saveUser(user);
    }
}
