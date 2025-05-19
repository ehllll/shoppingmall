package com.example.shoppingmall.domain.board.service;

import org.springframework.stereotype.Service;

import com.example.shoppingmall.domain.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

	private final BoardRepository boardRepository;
}
