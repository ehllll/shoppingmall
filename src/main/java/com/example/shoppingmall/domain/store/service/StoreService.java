package com.example.shoppingmall.domain.store.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.shoppingmall.domain.store.entity.Store;
import com.example.shoppingmall.domain.store.repository.StoreRepository;

@Service
@RequiredArgsConstructor
public class StoreService {

	private final StoreRepository storeRepository;


	@Transactional
	public void resetStores() {
		storeRepository.deleteAllInBatch(); //디비에 저장된 내용 삭제
	}

	@Transactional
	public void importCsv(String filePath){
		storeRepository.deleteAllInBatch();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			boolean isFirst = true;
			List<Store> stores = new ArrayList<>();
			int batchSize = 1000;
			int totalCount = 0;
			int lineCount = 0;
			int maxLines = 20; // 테스트용 제한
			int saveCount = 0;

			while ((line = br.readLine()) != null) {
				if (isFirst) {
					isFirst = false; // 첫 줄(헤더) 건너뜀
					continue;
				}

				if (lineCount >= maxLines) break;
				lineCount++;

				String[] tokens = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				for (int i = 0; i < tokens.length; i++) {
					tokens[i] = tokens[i].trim().replaceAll("^\"|\"$", "");
				}

				Store store = Store.builder()
					.companyName(tokens[0])
					.storeName(tokens[1])
					.rating(Long.parseLong(tokens[10]))
					.status(tokens[9])
					.monitoringDate(LocalDate.parse(tokens[31]))
					.build();

				stores.add(store);

				if (stores.size() >= batchSize) {
					storeRepository.saveAll(stores);
					storeRepository.flush();
					saveCount++;
					totalCount += stores.size();  // 누적 저장 수 증가
					System.out.println("현재까지 저장된 건수: " + totalCount);
					stores.clear();
				}
			}
			if (!stores.isEmpty()) {
				System.out.println(" 마지막 저장 시작: " + stores.size());
				storeRepository.saveAll(stores);
				storeRepository.flush();
				saveCount++;
				System.out.println(" 마지막 저장 완료");
			}
			System.out.println("총 saveAll() 호출 횟수: " + saveCount);
			System.out.println("INSERT 완료");
		} catch (IOException e) {
			throw new RuntimeException("CSV 파일 읽기 실패: " + e.getMessage());
		}
	}

	public List<Store> getFilteredStores(Long rating, String status) {
		Pageable top10 = PageRequest.of(0, 10);
		return storeRepository.findFilteredStores(rating, status, top10);
	}

	public Page<Store> getFilteredStoresPaged(Long rating, String status,Pageable pageable) {
		return storeRepository.findFilteredStoresPaged(rating, status, pageable);
	}
}
