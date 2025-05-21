package com.example.shoppingmall.domain.store.dto;

import java.util.List;

import lombok.Getter;

import org.springframework.data.domain.Page;

@Getter
public class PagedResponseDto<T> {
	private List<T> content;
	private int page;
	private int size;
	private long totalElements;
	private int totalPages;
	private boolean last;

	public PagedResponseDto(Page<T> page) {
		this.content = page.getContent();
		this.page = page.getNumber();
		this.size = page.getSize();
		this.totalElements = page.getTotalElements();
		this.totalPages = page.getTotalPages();
		this.last = page.isLast();
	}
}
