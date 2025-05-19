package com.example.shoppingmall.domain.user.controller;

import com.example.shoppingmall.domain.user.dto.request.SignUpRequestDto;
import com.example.shoppingmall.domain.user.dto.response.SignUpResponseDto;
import com.example.shoppingmall.domain.user.service.UserService;
import com.example.shoppingmall.global.common.enums.SuccessCode;
import com.example.shoppingmall.global.common.response.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shoppingmall")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup") //회원가입
    public ResponseEntity<ApiResponseDto<SignUpResponseDto>> signUp(@RequestBody SignUpRequestDto dto) {

        SignUpResponseDto signUpResponseDto = userService.signUp(dto);

        return ResponseEntity.ok(ApiResponseDto.success(SuccessCode.USER_CREATE_SUCCESS, signUpResponseDto));
    }
}
