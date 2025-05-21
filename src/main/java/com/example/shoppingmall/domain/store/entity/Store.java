package com.example.shoppingmall.domain.store.entity;

import java.time.LocalDate;
import java.util.Objects;

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

@Entity
@Table(name = "stores")
@Getter
@Builder
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

	public Store(String companyName, String storeName, Long rating, String status, LocalDate monitoringDate) {
		this.companyName = companyName;
		this.storeName = storeName;
		this.rating = rating;
		this.status = status;
		this.monitoringDate = monitoringDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Store store = (Store) o;
		return Objects.equals(id, store.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
