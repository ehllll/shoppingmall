package com.example.shoppingmall.global.common.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public record TokenResponse(String accessToken, String refreshToken) {

    /*
    record 클래스의 특성
    record의 필드는 암묵적으로 private, final로 선언이 된다.
    자동 생성되는 요소:
    생성자 (Canonical Constructor)
    toString(), equals() 및 hashCode() 메서드
    * */
}
