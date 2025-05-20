package com.example.shoppingmall.domain.board.entity;

import com.example.shoppingmall.domain.board.dto.BoardUpdateRequestDto;
import com.example.shoppingmall.global.common.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board")
@NoArgsConstructor
@Getter
public class Board extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	private Board board;


	public Board(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public void updatedAt(BoardUpdateRequestDto boardUpdateRequestDto) {
		if(boardUpdateRequestDto.getTitle() != null) {
			this.title = boardUpdateRequestDto.getTitle();
		}
		if(boardUpdateRequestDto.getContent() != null) {
			this.content = boardUpdateRequestDto.getContent();
		}
	}
}

