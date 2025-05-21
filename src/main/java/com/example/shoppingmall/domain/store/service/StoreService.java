package com.example.shoppingmall.domain.store.service;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.shoppingmall.domain.store.entity.Store;
import com.example.shoppingmall.domain.store.repository.StoreRepository;

@Service
@RequiredArgsConstructor
public class StoreService {

	private final StoreRepository storeRepository;

	public List<Store> getFilteredStores(Long rating, String status) {
		Pageable top10 = PageRequest.of(0, 10);
		return storeRepository.findFilteredStores(rating, status, top10);
	}
}
