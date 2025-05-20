package com.example.shoppingmall.domain.review.dto;

import lombok.*;

import java.time.LocalDateTime;

public class ReviewDto {

    // 리뷰 작성 요청 DTO
    @Getter
    @NoArgsConstructor
    public static class CreateRequest {
        private Long storeId;
        private String content;
        private int rating;
    }

    // 리뷰 수정 요청 DTO
    @Getter
    @NoArgsConstructor
    public static class UpdateRequest {
        private String content;
        private int rating;
    }

    // 리뷰 응답 DTO
    @Getter
    @Builder
    @AllArgsConstructor
    public static class Response {
        private Long reviewId;
        private Long storeId;
        private Long userId;
        private String content;
        private int rating;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    // 평균 평점 응답 DTO
    @Getter
    @Builder
    @AllArgsConstructor
    public static class AverageRatingResponse {
        private Long storeId;
        private double averageRating;
        private int reviewCount;
    }
}
