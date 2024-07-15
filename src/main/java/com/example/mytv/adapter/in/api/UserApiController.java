package com.example.mytv.adapter.in.api;

import com.example.mytv.application.port.in.UserUserCase;
import com.example.mytv.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserApiController {
    private final UserUserCase userUserCase;

    @GetMapping
    public User getUSer(
        User user
    ) {
        return user;
    }
}
