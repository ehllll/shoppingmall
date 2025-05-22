package com.example.shoppingmall.domain.board.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
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
import com.example.shoppingmall.domain.store.repository.StoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

	private final BoardRepository boardRepository;
	private final StoreRepository storeRepository;
	private final RedisTemplate<String, String> redisTemplate;

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
	public BoardResponseDto findById(Long id, Long storeId, Long userId) {
		Board board = boardRepository.findByIdAndStoreId(id, storeId).orElseThrow(
			() -> new RuntimeException("문의사항이 존재하지 않습니다")
		);

		String viewKey = "board:view" + id;
		String viewedKey = "board:viewed:" + userId + ":" + id;
		String rankingKey = "board:ranking";

		//중복조회방지
		if (!Boolean.TRUE.equals(redisTemplate.hasKey(viewedKey))) {
			redisTemplate.opsForValue().increment(viewedKey);
			redisTemplate.expire(viewedKey, Duration.ofMinutes(30));
			redisTemplate.opsForZSet().incrementScore(rankingKey, id.toString(), 1.0);
		}

		return new BoardResponseDto(board);
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

	@Override
	public List<Long> getTopRankedBoards() {
		Set<ZSetOperations.TypedTuple<String>> top = redisTemplate.opsForZSet()
			.reverseRangeWithScores("board:ranking", 0, 9);

		return top.stream()
			.map(ZSetOperations.TypedTuple::getValue)
			.map(Long::valueOf)
			.collect(Collectors.toList());
	}

}

