package com.example.shoppingmall.global.common.auth.jwt;


import com.example.shoppingmall.domain.user.entity.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserAuth {
    private final String username;
    private final UserRole role;
}
