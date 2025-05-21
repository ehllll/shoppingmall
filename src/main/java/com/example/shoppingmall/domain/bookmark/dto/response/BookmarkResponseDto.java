package com.example.shoppingmall.domain.bookmark.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BookmarkResponseDto {

    private Long storeId;

    private String storeName;

    private LocalDateTime createdAt;


}
