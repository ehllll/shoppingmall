package com.example.shoppingmall.domain.user.dto.request;

import com.example.shoppingmall.domain.user.entity.UserAuthority;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignUpRequestDto {

    private final String nickname;
    private final String email;
    private final String password;
    private final String address;
    private final UserAuthority userAuthority;


    public SignUpRequestDto(String nickname, String email, String password, String address, UserAuthority userAuthority) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.address = address;
        this.userAuthority = userAuthority;
    }
}
