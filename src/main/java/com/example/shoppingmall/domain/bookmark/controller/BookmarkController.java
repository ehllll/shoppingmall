package com.example.shoppingmall.domain.bookmark.controller;

import com.example.shoppingmall.domain.bookmark.dto.request.BookmarkRequestDto;
import com.example.shoppingmall.domain.bookmark.dto.response.BookmarkResponseDto;
import com.example.shoppingmall.domain.bookmark.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor

public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/stores/{storeId}/bookmarks")
    ResponseEntity<Void> createBookmark(
            @PathVariable Long storeId,
            @RequestBody BookmarkRequestDto request
    ){
        bookmarkService.createBookmark(storeId,request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/auths/me/bookmarks")
    ResponseEntity<List<BookmarkResponseDto>> findBookmark(){
        return ResponseEntity.ok().body(bookmarkService.findAll());
    }

}
