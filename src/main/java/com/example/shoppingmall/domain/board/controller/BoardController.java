package com.example.shoppingmall.domain.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.shoppingmall.domain.board.dto.BoardRequestDto;
import com.example.shoppingmall.domain.board.dto.BoardResponseDto;
import com.example.shoppingmall.domain.board.dto.BoardUpdateRequestDto;
import com.example.shoppingmall.domain.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;

	@PostMapping("/stores/{storeId}/boards")
	public ResponseEntity<BoardResponseDto> createPost(@PathVariable Long storeId, @RequestBody BoardRequestDto boardRequestDto) {
		BoardResponseDto boardResponseDto = BoardService.createPost(storeId, boardRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(boardResponseDto);
	}

	@GetMapping("/stores/{storeId}/boards/{boardId}")
	public ResponseEntity<BoardResponseDto> findById(@PathVariable Long storeId , @PathVariable Long boardId) {
		BoardResponseDto boardResponseDto = BoardService.findById(storeId, boardId);
		return ResponseEntity.ok(boardResponseDto);
	}

	@GetMapping("/stores/{storeId}/boards")
	public ResponseEntity<List<BoardResponseDto>> getAll() {
		return ResponseEntity.ok(boardService.getAll());
	}

	@PutMapping("/stores/{storeId}/boards/{boardId}}")
	public ResponseEntity<String> updatePost(@PathVariable Long storeId, @PathVariable Long boardId, @RequestBody BoardUpdateRequestDto boardUpdateRequestDto) {
		BoardService.updatePost(storeId, boardId, boardUpdateRequestDto);
		return ResponseEntity.ok("문의사항 수정 완료");
	}


	@DeleteMapping("/stores/{storeId}/boards/{boardId}")
	public ResponseEntity<String> deletePost(@PathVariable Long storeId, @PathVariable Long boardId, @RequestBody BoardRequestDto boardRequestDto) {
		BoardService.deletePost(storeId,boardId,boardRequestDto);
		return ResponseEntity.ok("문의사항 삭제 완료");
	}
}