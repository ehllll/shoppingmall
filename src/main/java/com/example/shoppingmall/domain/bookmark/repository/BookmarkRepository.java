package com.example.shoppingmall.domain.bookmark.repository;

import com.example.shoppingmall.domain.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {

    List<Bookmark> findAllByUserId(Long userId);

    boolean existsByUserIdAndStoreId(Long userId, Long storeId);

    Optional<Bookmark> findByUserIdAndStoreId(Long userId, Long storeId);
}
