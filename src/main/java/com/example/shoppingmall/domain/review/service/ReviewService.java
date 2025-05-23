package com.example.shoppingmall.domain.review.service;

import com.example.shoppingmall.domain.review.dto.ReviewDto;
import com.example.shoppingmall.global.common.auth.dto.request.AuthUser;

import java.util.List;

public interface ReviewService {

    // 리뷰 생성
    void createReview(ReviewDto.CreateRequest request, AuthUser authUser);

    // 리뷰 수정
    void updateReview(Long reviewId, ReviewDto.UpdateRequest request, AuthUser authUser);

    // 리뷰 삭제 (소프트 딜리트)
    void deleteReview(Long reviewId, AuthUser authUser);

    // 스토어별 리뷰 목록 조회
    List<ReviewDto.Response> getReviewsByStore(Long storeId);

    // 스토어별 평균 평점 조회
    ReviewDto.AverageRatingResponse getAverageRating(Long storeId);
}
