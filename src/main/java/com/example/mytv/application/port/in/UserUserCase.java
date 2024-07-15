package com.example.mytv.application.port.in;

import com.example.mytv.domain.User;

public interface UserUserCase {
    User getUser(String userId);
}
