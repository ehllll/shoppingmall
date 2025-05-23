package com.example.shoppingmall.global.common.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class SignInRequestDto {

	@NotBlank
	private final String username;

	@NotBlank
	private final String password;

	public SignInRequestDto(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
