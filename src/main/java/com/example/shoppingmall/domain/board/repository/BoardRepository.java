package com.example.shoppingmall.domain.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shoppingmall.domain.board.entity.Board;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

	Optional<Board> findByIdAndStoreId(Long id, Long storeId);

	@Modifying
	@Query("UPDATE Board b SET b.viewCount = b.viewCount + :count WHERE b.id :id")
	void incrementViewCount(@Param("id") Long id, @Param("count") int count);
}
