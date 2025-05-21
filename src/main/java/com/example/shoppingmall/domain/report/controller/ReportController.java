package com.example.shoppingmall.domain.report.controller;

import com.example.shoppingmall.domain.report.dto.request.CreateReportRequest;
import com.example.shoppingmall.domain.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stores/{storeId}")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/reports")
    ResponseEntity<Void> createReport(@PathVariable Long storeId,
                                      @RequestBody CreateReportRequest request
    ){
        Long userId = getCurrentUserId();
        reportService.createReport(userId,storeId,request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

