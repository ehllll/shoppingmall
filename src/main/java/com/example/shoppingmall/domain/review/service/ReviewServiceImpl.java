package com.example.shoppingmall.domain.review.service;

import com.example.shoppingmall.domain.review.dto.ReviewDto;
import com.example.shoppingmall.domain.review.entity.Review;
import com.example.shoppingmall.domain.review.repository.ReviewRepository;
import com.example.shoppingmall.global.common.auth.dto.request.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    // 리뷰 생성
    @Override
    @Transactional
    public void createReview(ReviewDto.CreateRequest request, AuthUser authUser) {
        // 동일한 유저가 동일한 스토어에 이미 리뷰 작성했는지 확인
        Optional<Review> existing = reviewRepository.findByUserIdAndStoreIdAndDeletedFalse(authUser.getId(), request.getStoreId());
        if (existing.isPresent()) {
            throw new IllegalStateException("이미 리뷰를 작성하셨습니다.");
        }

        // 리뷰 생성 및 저장
        Review review = new Review(authUser.getId(), request.getStoreId(), request.getContent(), request.getRating());
        reviewRepository.save(review);
    }

    // 리뷰 수정
    @Override
    @Transactional
    public void updateReview(Long reviewId, ReviewDto.UpdateRequest request, AuthUser authUser) {
        // 리뷰 존재 여부 확인
        Review review = reviewRepository.findByIdAndDeletedFalse(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        // 작성자 본인 확인
        if (!review.getUserId().equals(authUser.getId())) {
            throw new SecurityException("본인의 리뷰만 수정할 수 있습니다.");
        }

        // 기존 내용과 동일하면 수정 불가
        if (review.getContent().equals(request.getContent()) && review.getRating() == request.getRating()) {
            throw new IllegalStateException("수정된 내용이 없습니다.");
        }

        // 리뷰 수정
        review.update(request.getContent(), request.getRating());
    }

    // 리뷰 삭제 (소프트 딜리트)
    @Override
    @Transactional
    public void deleteReview(Long reviewId, AuthUser authUser) {
        // 리뷰 존재 여부 확인
        Review review = reviewRepository.findByIdAndDeletedFalse(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        // 작성자 본인 확인
        if (!review.getUserId().equals(authUser.getId())) {
            throw new SecurityException("본인의 리뷰만 삭제할 수 있습니다.");
        }

        // 삭제 처리
        review.softDelete();
    }

    // 스토어별 리뷰 목록 조회
    @Override
    @Transactional(readOnly = true)
    public List<ReviewDto.Response> getReviewsByStore(Long storeId) {
        // 삭제되지 않은 리뷰만 조회 후 DTO로 변환
        return reviewRepository.findByStoreIdAndDeletedFalse(storeId)
                .stream()
                .map(review -> ReviewDto.Response.builder()
                        .reviewId(review.getId())
                        .storeId(review.getStoreId())
                        .userId(review.getUserId())
                        .content(review.getContent())
                        .rating(review.getRating())
                        .createdAt(review.getCreatedAt())
                        .updatedAt(review.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    // 스토어별 평균 평점 조회
    @Override
    @Transactional(readOnly = true)
    public ReviewDto.AverageRatingResponse getAverageRating(Long storeId) {
        List<Review> reviews = reviewRepository.findAllByStoreIdAndDeletedFalse(storeId);
        int total = reviews.size();

        // 평균 계산 (0건이면 0.0 반환)
        double avg = total == 0 ? 0.0 :
                reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);

        return ReviewDto.AverageRatingResponse.builder()
                .storeId(storeId)
                .averageRating(avg)
                .reviewCount(total)
                .build();
    }
}
