package com.example.shoppingmall.domain.user.dto.request;

import lombok.Getter;

@Getter
public record SignInRequestDto(String username, String password) {


}
