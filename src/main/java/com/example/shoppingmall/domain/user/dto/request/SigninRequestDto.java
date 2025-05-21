package com.example.shoppingmall.domain.user.dto.request;

import lombok.Getter;

@Getter
public class SigninRequestDto {

    private final String username;
    private final String password;

    public SigninRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
