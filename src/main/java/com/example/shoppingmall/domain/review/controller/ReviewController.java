package com.example.shoppingmall.domain.review.controller;

import com.example.shoppingmall.domain.review.dto.ReviewDto;
import com.example.shoppingmall.domain.review.service.ReviewService;
import com.example.shoppingmall.global.common.auth.dto.request.AuthUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 작성
    @PostMapping
    public ResponseEntity<Void> createReview(
            @RequestBody @Valid ReviewDto.CreateRequest request,
            @RequestAttribute AuthUser authUser) {

        reviewService.createReview(request, authUser);
        return ResponseEntity.ok().build();
    }

    // 리뷰 수정
    @PutMapping("/{reviewId}")
    public ResponseEntity<Void> updateReview(
            @PathVariable Long reviewId,
            @RequestBody @Valid ReviewDto.UpdateRequest request,
            @RequestAttribute AuthUser authUser) {

        reviewService.updateReview(reviewId, request, authUser);
        return ResponseEntity.ok().build();
    }

    // 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long reviewId,
            @RequestAttribute AuthUser authUser) {

        reviewService.deleteReview(reviewId, authUser);
        return ResponseEntity.ok().build();
    }

    // 리뷰 목록 조회
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ReviewDto.Response>> getReviewsByStore(
            @PathVariable Long storeId) {

        List<ReviewDto.Response> response = reviewService.getReviewsByStore(storeId);
        return ResponseEntity.ok(response);
    }

    // 평균 평점 조회
    @GetMapping("/store/{storeId}/average-rating")
    public ResponseEntity<ReviewDto.AverageRatingResponse> getAverageRating(
            @PathVariable Long storeId) {

        ReviewDto.AverageRatingResponse response = reviewService.getAverageRating(storeId);
        return ResponseEntity.ok(response);
    }
}
