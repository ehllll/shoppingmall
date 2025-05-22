package com.example.shoppingmall.domain.report.dto.response;
import lombok.AllArgsConstructor;
import lombok.Getter;


import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
public class FindReportResponse {

    private Long id;

    private String username;

    private Long storeId;

    private String reason;

    private LocalDateTime createdAt;



}
