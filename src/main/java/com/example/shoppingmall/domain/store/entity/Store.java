package com.example.shoppingmall.domain.store.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stores")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Store {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "company_name")
	private String companyName;

	@Column(name = "store_name")
	private String storeName;

	@Column(name = "total_rating")
	private Long rating; // 전체평가

	@Column(name = "business_status")
	private String status; // 업소상태

	@Column(name = "monitoring_date")
	private LocalDate monitoringDate; // 모니터링날짜
}
