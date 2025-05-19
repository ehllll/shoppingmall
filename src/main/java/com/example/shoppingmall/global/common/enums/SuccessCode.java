package com.example.shoppingmall.global.common.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SuccessCode {
    //유져
    USER_CREATE_SUCCESS(201, HttpStatus.CREATED, "회원 가입이 완료되었습니다."),
    USER_UPDATE_SUCCESS(200, HttpStatus.OK, "회원 정보가 수정되었습니다."),
    USER_DELETE_SUCCESS(200, HttpStatus.OK, "회원 탈퇴가 완료되었습니다."),
    LOGOUT_SUCCESS(200, HttpStatus.OK, "로그아웃 되었습니다.");

    //todo 밑에 채워주시면 감사하겠습니다!

    //Board

    //BookMark

    //Report

    //Review

    //Store

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;

    SuccessCode(Integer code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
    }
