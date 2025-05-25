package com.example.shoppingmall.domain.store.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.Timestamp;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.example.shoppingmall.domain.store.entity.Store;
import com.example.shoppingmall.domain.store.repository.StoreRepository;

@Service
@RequiredArgsConstructor
public class StoreService {

	private final StoreRepository storeRepository;
	private final JdbcTemplate jdbcTemplate;


	@PersistenceContext
	private EntityManager em;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void resetStores() {
		em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
		em.createNativeQuery("TRUNCATE TABLE stores").executeUpdate();
		em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate(); // ID도 1로 초기화됨
	}

	@Transactional
	public void importCsv(String filePath){
		resetStores();
		List<Store> stores = new ArrayList<>();
		int batchSize = 500;
		int count = 0;

		System.out.println("CSV 파일 로딩 시도 중...");
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			System.out.println("CSV 파일 열기 성공"); // ← 이게 출력 안 되면 경로 문제
			String line;
			boolean isFirst = true;

			while ((line = br.readLine()) != null) {
				if (isFirst) {
					isFirst = false; // 첫 줄(헤더) 건너뜀
					continue;
				}

				String[] tokens = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				for (int i = 0; i < tokens.length; i++) {
					tokens[i] = tokens[i].trim().replaceAll("^\"|\"$", "");
				}

				Store store = new Store(
					tokens[0],            // company_name
					tokens[1],            // store_name
					Long.parseLong(tokens[10]), // total_rating
					tokens[9],            // business_status
					LocalDate.parse(tokens[31]) // monitoring_date)
				);

				stores.add(store);
				count++;
				if(count%batchSize == 0){
					System.out.println("5000완료1");
					saveAllCategoryStores(stores);
					stores.clear();
					System.out.println("5000완료2");
					break;
				}
			}

			System.out.println("JDBC INSERT 완료");
		} catch (IOException e) {
			throw new RuntimeException("CSV 파일 읽기 실패: " + e.getMessage());
		}
	}
	private void executeBatch(List<Object[]> batch) {
		jdbcTemplate.batchUpdate(
			"INSERT INTO stores (company_name, store_name, total_rating, business_status, monitoring_date) VALUES (?, ?, ?, ?, ?)",
			batch
		);
	}
	@Transactional
	public void saveAllCategoryStores(List<Store> stores) {
		jdbcTemplate.batchUpdate("INSERT INTO stores (company_name, store_name, total_rating, business_status, monitoring_date) values (?, ?, ?, ?, ?)",
			new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Store store = stores.get(i);
					ps.setString(1, store.getCompanyName());
					ps.setString(2, store.getStoreName());
					ps.setLong(3, store.getRating());
					ps.setString(4, store.getStatus());
					ps.setDate(5, java.sql.Date.valueOf(store.getMonitoringDate()));
				}

				@Override
				public int getBatchSize() {
					return stores.size();
				}
			});
	}

	public List<Store> getFilteredStores(Long rating, String status) {
		Pageable top10 = PageRequest.of(0, 10);
		return storeRepository.findFilteredStores(rating, status, top10);
	}

	public Page<Store> getFilteredStoresPaged(Long rating, String status,Pageable pageable) {
		return storeRepository.findFilteredStoresPaged(rating, status, pageable);
	}
}
