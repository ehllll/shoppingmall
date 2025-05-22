package com.example.shoppingmall.domain.report.repository;

import com.example.shoppingmall.domain.report.entity.Report;
import com.example.shoppingmall.domain.store.entity.Store;
import com.example.shoppingmall.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report,Long> {


    List<Report> findAllByStore(Store store);

    boolean existsByUserAndStore(User user, Store store);

    int countByStoreid(Long storeId);
}
