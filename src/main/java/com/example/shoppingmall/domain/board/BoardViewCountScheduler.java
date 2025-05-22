package com.example.shoppingmall.domain.board;

import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.shoppingmall.domain.board.dto.BoardRequestDto;
import com.example.shoppingmall.domain.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class BoardViewCountScheduler {

	private final RedisTemplate<String, String> redisTemplate;
	private final BoardRepository boardRepository;

	@Scheduled(cron = "0 0 0 * * *")
	public void syncAndClearViewCounts() {
		Set<String> keys = redisTemplate.keys("board:view:*");

		if (keys != null) {
			for (String key : keys) {
				Long boardId = Long.valueOf(key.replace("board:view:", ""));
				String countStr = redisTemplate.opsForValue().get(key);
				if(countStr != null) {
					int count = Integer.parseInt(countStr);
					boardRepository.incrementViewCount(boardId, count);
				}
			}
			redisTemplate.delete(keys);
			redisTemplate.delete("board:ranking");
		}
	}
}
