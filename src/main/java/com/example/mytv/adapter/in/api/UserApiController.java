package com.example.mytv.adapter.in.api;

import static com.example.mytv.adapter.in.api.constant.HeaderAttribute.X_AUTH_KEY;

import com.example.mytv.adapter.in.api.constant.HeaderAttribute;
import com.example.mytv.application.port.in.UserUserCase;
import com.example.mytv.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserApiController {


    @GetMapping
    public User getUSer(
        User user
    ) {
        return user;
    }
}
