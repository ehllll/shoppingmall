package com.example.shoppingmall.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.example.shoppingmall.domain.board.entity.Board;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDto {

	private Long id;
	private String title;
	private String content;

	public BoardResponseDto(Board board) {
		this.id = board.getId();
		this.title = board.getTitle();
		this.content = board.getContent();
	}
}

