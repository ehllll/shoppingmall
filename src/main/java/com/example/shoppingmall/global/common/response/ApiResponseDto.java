package com.example.shoppingmall.global.common.response;

import com.example.shoppingmall.global.common.enums.SuccessCode;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
public class ApiResponseDto<T> {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime timestamp;

    private final String message;

    @JsonInclude(value = NON_NULL)  // 실제 응답 데이터 (nullable), null이면 JSON에서 제외됨
    private final T data;

    public ApiResponseDto(SuccessCode successCode, T data) {
        this.timestamp = LocalDateTime.now();
        this.message = successCode.getMessage();
        this.data = data;
    }

    //성공 응답 처리
    public static <T> ApiResponseDto<T> success(SuccessCode successCode,T data) {

        return new ApiResponseDto<>(successCode, data);
    }
}
