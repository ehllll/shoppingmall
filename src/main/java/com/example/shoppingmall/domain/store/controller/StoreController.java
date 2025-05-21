package com.example.shoppingmall.domain.store.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.shoppingmall.domain.store.entity.Store;
import com.example.shoppingmall.domain.store.service.StoreService;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

	private final StoreService storeService;

	@GetMapping
	public ResponseEntity<List<Store>> getStores(
		@RequestParam(required = false) Long rating,
		@RequestParam(required = false) String status) {

		List<Store> stores = storeService.getFilteredStores(rating, status);
		return new ResponseEntity<>(stores,HttpStatus.OK);
	}
}
