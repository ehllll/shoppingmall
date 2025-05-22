package com.example.shoppingmall.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shoppingmall.domain.board.entity.Board;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}
