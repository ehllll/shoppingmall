package com.example.shoppingmall.domain.board.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.shoppingmall.domain.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class BoardController {

	private final BoardService boardService;


}
