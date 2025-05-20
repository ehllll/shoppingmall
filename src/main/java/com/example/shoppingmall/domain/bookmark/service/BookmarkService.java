package com.example.shoppingmall.domain.bookmark.service;

import com.example.shoppingmall.domain.bookmark.dto.request.BookmarkRequestDto;
import com.example.shoppingmall.domain.bookmark.dto.response.BookmarkResponseDto;
import com.example.shoppingmall.domain.bookmark.entity.Bookmark;
import com.example.shoppingmall.domain.bookmark.repository.BookmarkRepository;
import com.example.shoppingmall.domain.store.entity.Store;
import com.example.shoppingmall.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    public void createBookmark(Long storeId, BookmarkRequestDto request) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(()->new RuntimeException("찾을 수 없는 스토어"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(()->new RuntimeException("찾을 수 없는 스토어"));

        Bookmark bookmark = new Bookmark(user, store);
        bookmarkRepository.save(bookmark);

    }

    public List<BookmarkResponseDto> findAll() {

        List<Bookmark> bookmarks = bookmarkRepository.findAll();
        List<BookmarkResponseDto> result = new ArrayList<>();
        for (Bookmark bookmark : bookmarks) {
            BookmarkResponseDto bookmarkResponseDto = new BookmarkResponseDto(
                    bookmark.getUser().getId(),
                    bookmark.getStore().getId(),
                    bookmark.getStore().getName(),
                    bookmark.getCreatedAt());
            result.add(bookmarkResponseDto);
        }
        return result;
    }

    public void deleteBookmark(Long storeId, Long userId) {
        Bookmark bookmark = bookmarkRepository.findByUserIdAndStoreId(userId,storeId)
                        .orElseThrow(()->new RuntimeException("삭제할 북마크가 없습니다"));
        bookmarkRepository.delete(bookmark);
    }
}
