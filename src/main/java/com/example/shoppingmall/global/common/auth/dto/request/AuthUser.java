package com.example.shoppingmall.global.common.auth.dto.request;

import lombok.Getter;

@Getter
public class AuthUser {

    private final Long id;
    private final String username;

    public AuthUser(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
