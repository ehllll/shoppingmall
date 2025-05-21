package com.example.shoppingmall.domain.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.shoppingmall.domain.board.dto.BoardRequestDto;
import com.example.shoppingmall.domain.board.dto.BoardResponseDto;
import com.example.shoppingmall.domain.board.dto.BoardUpdateRequestDto;
import com.example.shoppingmall.domain.board.entity.Board;
import com.example.shoppingmall.domain.board.repository.BoardRepository;
import com.example.shoppingmall.domain.store.entity.Store;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

	private final BoardRepository boardRepository;

	@Override
	public BoardResponseDto createPost(Long storeId, BoardRequestDto boardRequestDto) {

		if(boardRequestDto.getTitle()==null || boardRequestDto.getTitle().isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목은 필수입니다.");
		}

		if(boardRequestDto.getContent() == null || boardRequestDto.getContent().isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "내용은 필수입니다.");
		}

		Store store = storeRepository.findById(storeId)
			.orElseThrow(() -> new IllegalArgumentException("스토어를 찾을 수 없습니다."));


		Board board = new Board(boardRequestDto.getTitle(), boardRequestDto.getContent());
		Board saved = boardRepository.save(board);

		return new BoardResponseDto(
			saved.getId(),
			saved.getTitle(),
			saved.getContent()
		);
	}


	@Override
	public BoardResponseDto findById(Long id) {
		Board board = boardRepository.findById(id).orElseThrow(
			() -> new RuntimeException("문의사항이 존재하지 않습니다")
		);
		return new BoardResponseDto(board.getId(), board.getTitle(), board.getContent());
	}


	@Override
	public List<BoardResponseDto> getAll() {
		List<Board> boardList = boardRepository.findAll();
		List<BoardResponseDto> responseDtoList = new ArrayList<>();
		for(Board board : boardList) {
			BoardResponseDto boardResponseDto = new BoardResponseDto(board.getId(), board.getTitle(), board.getContent());
			responseDtoList.add(boardResponseDto);
		}
		return responseDtoList;
	}


	@Transactional
	@Override
	public void updatePost(Long storeId, Long id, BoardUpdateRequestDto boardUpdateRequestDto) {
		Board board = boardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("문의사항을 찾을 수 없습니다"));

		board.updatedAt(boardUpdateRequestDto);
	}


	@Override
	public void deletePost(Long storeId, Long id, BoardRequestDto boardRequestDto) {
		Board board = boardRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("문의사항을 찾을 수 없습니다"));

		boardRepository.delete(board);
	}


}

