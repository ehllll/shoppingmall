package com.example.shoppingmall.domain.bookmark.service;
import com.example.shoppingmall.domain.bookmark.dto.response.BookmarkResponseDto;
import com.example.shoppingmall.domain.bookmark.entity.Bookmark;
import com.example.shoppingmall.domain.bookmark.repository.BookmarkRepository;
import com.example.shoppingmall.domain.error.CustomException;
import com.example.shoppingmall.domain.error.ErrorCode;
import com.example.shoppingmall.domain.store.entity.Store;
import com.example.shoppingmall.domain.user.entity.User;
import com.example.shoppingmall.domain.user.repository.UserRepository;
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

    public void createBookmark(Long userId, Long storeId) {
        if (bookmarkRepository.existsByUserIdAndStoreId(userId, storeId)) {
            throw new IllegalStateException("이미 즐겨찾기된 가게입니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

        Bookmark bookmark = new Bookmark(user, store);
        bookmarkRepository.save(bookmark);
    }

    public List<BookmarkResponseDto> findAllByUser(Long userId) {
        List<Bookmark> bookmarks = bookmarkRepository.findAllByUserId(userId);
        List<BookmarkResponseDto> result = new ArrayList<>();

        for (Bookmark bookmark : bookmarks) {
            Store store = bookmark.getStore();
            BookmarkResponseDto dto = new BookmarkResponseDto(
                    store.getId(),
                    store.getName(),
                    store.getCreatedAt(),
                    store.getaddress()
            );
            result.add(dto);
        }

        return result;
    }


    public void deleteBookmark(Long storeId, Long userId) {
        Bookmark bookmark = bookmarkRepository.findByUserIdAndStoreId(userId,storeId)
                        .orElseThrow(()->new RuntimeException("삭제할 북마크가 없습니다"));
        bookmarkRepository.delete(bookmark);
    }
}
