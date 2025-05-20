package com.example.shoppingmall.domain.store.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stores")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Store {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "companyName")
	private String companyName;

	@Column(name = "storeName")
	private String storeName;

	@Column(name = "totalRating")
	private Double rating; // 전체평가

	@Column(name = "businessStatus")
	private String status; // 업소상태

	@Column(name = "monitoringDate")
	private LocalDate monitoringDate; // 모니터링날짜
}
