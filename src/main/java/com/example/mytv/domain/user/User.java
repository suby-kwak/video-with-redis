package com.example.mytv.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class User {
    private String id;
    private String name;
    private String profileImageUrl;
}
