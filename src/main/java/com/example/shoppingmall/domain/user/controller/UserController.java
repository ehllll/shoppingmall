package com.example.shoppingmall.domain.user.controller;

import com.example.shoppingmall.domain.user.dto.request.SigninRequestDto;
import com.example.shoppingmall.domain.user.dto.request.SignUpRequestDto;
import com.example.shoppingmall.domain.user.dto.request.UpdatePasswordRequestDto;
import com.example.shoppingmall.domain.user.dto.response.SignUpResponseDto;
import com.example.shoppingmall.domain.user.dto.response.TokenResponse;
import com.example.shoppingmall.domain.user.service.UserService;
import com.example.shoppingmall.global.common.enums.SuccessCode;
import com.example.shoppingmall.global.common.response.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auths")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup") //회원가입
    public ResponseEntity<ApiResponseDto<TokenResponse>> signUp(@RequestBody SignUpRequestDto dto) {

        TokenResponse tokenResponse = userService.signUp(dto);

        return ResponseEntity.ok(ApiResponseDto.success(SuccessCode.USER_CREATE_SUCCESS, tokenResponse));
    }

    @PutMapping("/{id}") //비밀번호 업데이트
    public ResponseEntity<ApiResponseDto<Void>> updatePassword(@PathVariable Long id, @RequestBody UpdatePasswordRequestDto requestDto) {

        userService.updatePassword(id, requestDto);

        return ResponseEntity.ok(ApiResponseDto.success(SuccessCode.USER_UPDATE_SUCCESS, null));
    }

    @PostMapping("/signin") //로그인
    public ResponseEntity<ApiResponseDto<TokenResponse>> login(@RequestBody SigninRequestDto requestDto) {

        TokenResponse signinDto = userService.login(requestDto);

        return ResponseEntity.ok(ApiResponseDto.success(SuccessCode.LOGIN_SUCCESS, signinDto));
    }
}
