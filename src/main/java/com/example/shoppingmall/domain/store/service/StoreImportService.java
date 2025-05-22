package com.example.shoppingmall.domain.store.service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.example.shoppingmall.domain.store.entity.Store;

@Service
@RequiredArgsConstructor
public class StoreImportService {
	private final JdbcTemplate jdbcTemplate;

	private static final String INSERT_SQL = "INSERT INTO stores (company_name, store_name, total_rating, business_status, monitoring_date) " +
		"VALUES (?, ?, ?, ?, ?)";

	public void insertStores(List<Store> stores) {
		int batchSize = 1000;

		for (int i = 0; i < stores.size(); i += batchSize) {
			int end = Math.min(i + batchSize, stores.size());
			List<Store> batchList = stores.subList(i, end);

			jdbcTemplate.batchUpdate(INSERT_SQL, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					Store s = batchList.get(i);
					ps.setString(1, s.getCompanyName());
					ps.setString(2, s.getStoreName());
					ps.setLong(3, s.getRating());
					ps.setString(4, s.getStatus());
					ps.setDate(5, Date.valueOf(s.getMonitoringDate()));
				}

				@Override
				public int getBatchSize() {
					return batchList.size();
				}
			});
		}
	}
}
