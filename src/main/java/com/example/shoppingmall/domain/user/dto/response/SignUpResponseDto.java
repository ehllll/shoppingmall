package com.example.shoppingmall.domain.user.dto.response;

import com.example.shoppingmall.domain.user.entity.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignUpResponseDto {


    private final String nickname;
    private final String email;
    private final String address;
    private final UserRole userAuthority;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime updateAt;


    public SignUpResponseDto(String nickname, String email, String address, UserRole userAuthority, LocalDateTime updateAt) {
        this.nickname = nickname;
        this.email = email;
        this.address = address;
        this.userAuthority = userAuthority;
        this.updateAt = updateAt;
    }
}
