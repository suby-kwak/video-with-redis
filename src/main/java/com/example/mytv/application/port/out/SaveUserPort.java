package com.example.mytv.application.port.out;

import com.example.mytv.domain.user.User;

public interface SaveUserPort {
    String saveUser(User user);
}
