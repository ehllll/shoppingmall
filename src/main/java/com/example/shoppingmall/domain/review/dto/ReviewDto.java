package com.example.shoppingmall.domain.review.dto;

import jakarta.validation.constraints.*; // 유효성 검증용
import lombok.*;

import java.time.LocalDateTime;

public class ReviewDto {

    // 리뷰 작성 요청 DTO
    @Getter
    @NoArgsConstructor
    public static class CreateRequest {

        @NotNull(message = "스토어 ID는 필수입니다.")
        private Long storeId;

        @NotBlank(message = "리뷰 내용은 공백일 수 없습니다.")
        private String content;

        @Min(value = 1, message = "평점은 최소 1점 이상이어야 합니다.")
        @Max(value = 5, message = "평점은 최대 5점 이하여야 합니다.")
        private int rating;
    }

    // 리뷰 수정 요청 DTO
    @Getter
    @NoArgsConstructor
    public static class UpdateRequest {

        @NotBlank(message = "수정할 리뷰 내용은 공백일 수 없습니다.")
        private String content;

        @Min(value = 1, message = "평점은 최소 1점 이상이어야 합니다.")
        @Max(value = 5, message = "평점은 최대 5점 이하여야 합니다.")
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
