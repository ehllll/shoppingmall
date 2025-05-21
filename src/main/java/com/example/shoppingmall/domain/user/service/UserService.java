package com.example.shoppingmall.domain.user.service;

import com.example.shoppingmall.domain.user.dto.request.SignInRequestDto;
import com.example.shoppingmall.domain.user.dto.request.SignUpRequestDto;
import com.example.shoppingmall.domain.user.dto.request.UpdatePasswordRequestDto;
import com.example.shoppingmall.domain.user.dto.response.TokenResponse;

public interface UserService {
    TokenResponse signUp(SignUpRequestDto requestDto);

    void updatePassword(Long id, UpdatePasswordRequestDto requestDto);

    TokenResponse signIn(SignInRequestDto requestDto);
}
