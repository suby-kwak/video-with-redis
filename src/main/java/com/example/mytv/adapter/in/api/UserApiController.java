package com.example.mytv.adapter.in.api;

import com.example.mytv.adapter.in.api.dto.CommandResponse;
import com.example.mytv.application.port.in.UserUserCase;
import com.example.mytv.domain.user.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserApiController {

    private final UserUserCase userUserCase;

    public UserApiController(UserUserCase userUserCase) {
        this.userUserCase = userUserCase;
    }

    @GetMapping
    public User getUSer(User user) {
        return user;
    }

    @PostMapping("/create")
    public CommandResponse createUser(@RequestBody User request) {
        var user = userUserCase.createUser(request);

        return new CommandResponse(user);
    }
}
