package com.example.shoppingmall.domain.report.service;

import com.example.shoppingmall.domain.report.dto.request.CreateReportRequest;
import com.example.shoppingmall.domain.report.dto.response.FindReportResponse;
import com.example.shoppingmall.domain.report.entity.Report;
import com.example.shoppingmall.domain.report.repository.ReportRepository;
import com.example.shoppingmall.domain.store.entity.Store;
import com.example.shoppingmall.domain.user.entity.User;
import com.example.shoppingmall.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Nodes.collect;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    public void createReport(Long userId ,Long storeId, CreateReportRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        Store store = storeRepository.findById(storeId)
                .orElseThrow(()-> new RuntimeException("스토어를 찾을 수 없습니다."));
        Report report = new Report(user,store,request.getReason());
        reportRepository.save(report);
    }

    public List<FindReportResponse> findAllReport(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("스토어를 찾을 수 없습니다."));


        return reportRepository.findAllByStore(store)
                .stream()
                .map(report -> new FindReportResponse(
                        report.getId(),
                        report.getUser().getNickName(),
                        report.getStore().getId(),
                        report.getReason(),
                        report.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }
}
