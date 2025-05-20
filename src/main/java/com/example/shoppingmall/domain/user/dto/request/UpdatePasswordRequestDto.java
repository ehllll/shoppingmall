package com.example.shoppingmall.domain.user.dto.request;

import lombok.Getter;

@Getter
public class UpdatePasswordRequestDto {

    private final String password;

    public UpdatePasswordRequestDto(String password) {
        this.password = password;

    }
}
