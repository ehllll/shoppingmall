package com.example.shoppingmall.domain.user.service;

import com.example.shoppingmall.domain.user.dto.request.SignUpRequestDto;
import com.example.shoppingmall.domain.user.dto.request.UpdatePasswordRequestDto;
import com.example.shoppingmall.domain.user.dto.response.SignUpResponseDto;

public interface UserService {
    SignUpResponseDto signUp(SignUpRequestDto requestDto);

    void updatePassword(Long id, UpdatePasswordRequestDto requestDto);
}
