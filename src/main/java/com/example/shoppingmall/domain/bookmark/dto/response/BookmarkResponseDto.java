package com.example.shoppingmall.domain.bookmark.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BookmarkResponseDto {
    private Long bookmarkId;

    private Long userId;

    private Long storeId;

    private LocalDateTime createdAt;
}
