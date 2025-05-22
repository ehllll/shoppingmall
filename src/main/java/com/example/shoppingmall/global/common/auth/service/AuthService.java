package com.example.shoppingmall.global.common.auth.service;


import com.example.shoppingmall.global.common.auth.dto.request.SignInRequestDto;
import com.example.shoppingmall.global.common.auth.dto.response.TokenResponse;

public interface AuthService {
    TokenResponse signIn(SignInRequestDto requestDto);

    void logout(String refreshToken);
}
