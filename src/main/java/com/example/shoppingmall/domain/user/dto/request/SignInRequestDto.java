package com.example.shoppingmall.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public record SignInRequestDto(
        @NotBlank String username, @NotBlank String password) {
}
