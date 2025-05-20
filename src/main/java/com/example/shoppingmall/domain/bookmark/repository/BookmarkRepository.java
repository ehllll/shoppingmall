package com.example.shoppingmall.domain.bookmark.repository;

import com.example.shoppingmall.domain.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {
    Optional<Bookmark> findByUserIdAndStoreId(Long userId, Long storeId);
}
