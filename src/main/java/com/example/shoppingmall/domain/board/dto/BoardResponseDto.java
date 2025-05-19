package com.example.shoppingmall.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto {

	private Long id;
	private String title;
	private String content;
}
