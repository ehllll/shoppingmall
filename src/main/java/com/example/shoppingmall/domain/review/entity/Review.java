package com.example.shoppingmall.domain.review.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 리뷰 ID

    @Column(name = "user_id", nullable = false)
    private Long userId; // 작성자 ID

    @Column(name = "store_id", nullable = false)
    private Long storeId; // 리뷰 대상 쇼핑몰 ID

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 리뷰 내용

    @Column(nullable = false)
    private Integer rating; // 평점 (1~5)

    @Column(nullable = false)
    private Boolean deleted = false; // 삭제 여부 (소프트 삭제)

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 작성 시간

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt; // 마지막 수정 시간

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt; // 삭제된 시간

    // JPA가 엔티티 저장할 때 자동 호출되어 시간 세팅
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // JPA가 엔티티 수정할 때 자동 호출되어 수정 시간 업데이트
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // 생성자 (리뷰 생성 시 사용)
    public Review(Long userId, Long storeId, String content, Integer rating) {
        this.userId = userId;
        this.storeId = storeId;
        this.content = content;
        this.rating = rating;
        this.deleted = false;
    }

    public void update(String content, int rating) {
        this.content = content;
        this.rating = rating;
    }

    // 소프트 딜리트 처리
    public void softDelete() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}
