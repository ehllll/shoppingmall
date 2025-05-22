package com.example.shoppingmall.domain.board.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.example.shoppingmall.domain.board.dto.BoardRequestDto;
import com.example.shoppingmall.domain.board.dto.BoardResponseDto;
import com.example.shoppingmall.domain.board.dto.BoardUpdateRequestDto;

public interface BoardService {
	BoardResponseDto createPost(Long storeId, BoardRequestDto boardRequestDto);


	BoardResponseDto findById(Long id, Long storeId, Long userId);

	List<BoardResponseDto> getAll();

	@Transactional
	void updatePost(Long storeId, Long id, BoardUpdateRequestDto boardUpdateRequestDto);

	void deletePost(Long storeId, Long id, BoardRequestDto boardRequestDto);

	List<Long> getTopRankedBoards();
}
