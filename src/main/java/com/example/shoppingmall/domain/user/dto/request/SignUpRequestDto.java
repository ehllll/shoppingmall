package com.example.shoppingmall.domain.user.dto.request;

import com.example.shoppingmall.domain.user.entity.UserRole;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

    private final String nickname;
    private final String username;
    private final String password;
    private final String address;
    private final UserRole userAuthority;


    public SignUpRequestDto(String nickname, String username, String password, String address, UserRole userAuthority) {
        this.nickname = nickname;
        this.username = username;
		this.password = password;
        this.address = address;
        this.userAuthority = userAuthority;
    }
}
