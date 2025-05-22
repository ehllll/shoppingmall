package com.example.shoppingmall.global.common.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public record SignInRequestDto(
        @NotBlank String username, @NotBlank String password) {
}
