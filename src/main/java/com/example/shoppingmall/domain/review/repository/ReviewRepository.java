package com.example.shoppingmall.domain.review.repository;

import com.example.shoppingmall.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 스토어 기준 리뷰 목록 조회 (삭제되지 않은 것만)
    List<Review> findByStoreIdAndDeletedFalse(Long storeId);

    // 특정 유저가 특정 스토어에 리뷰를 작성했는지 (중복 방지)
    Optional<Review> findByUserIdAndStoreIdAndDeletedFalse(Long userId, Long storeId);

    // 리뷰 단건 조회 (삭제되지 않은 것만)
    Optional<Review> findByIdAndDeletedFalse(Long id);

    // 스토어별 평균 평점 계산을 위한 전체 리뷰 조회
    List<Review> findAllByStoreIdAndDeletedFalse(Long storeId);
}
