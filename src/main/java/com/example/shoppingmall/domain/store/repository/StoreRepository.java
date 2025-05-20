package com.example.shoppingmall.domain.store.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.shoppingmall.domain.store.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {

	@Query("SELECT s FROM Store s WHERE (:rating IS NULL OR s.rating = :rating) AND (:status IS NULL OR s.status = :status) ORDER BY s.monitoringDate DESC")
	List<Store> findFilteredStores(@Param("rating") Double rating, @Param("status") String status, Pageable pageable);
}

