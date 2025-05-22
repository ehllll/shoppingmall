package com.example.shoppingmall.domain.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //common
    INVALID_INPUT_VALUE(400, "Bad Request", "C001","잘못된 입력 값입니다."),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed","C002", "허용되지 않은 HTTP 메서드입니다."),
    ENTITY_NOT_FOUND(400, "Bad Request", "C003","엔티티를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "Server Error","C004", "내부 서버 오류가 발생했습니다."),
    INVALID_TYPE_VALUE(400, "Bad Request","C005", "잘못된 타입의 값입니다."),

    // User
    EMAIL_DUPLICATION(400, "Bad Request", "U001", "이미 사용 중인 이메일입니다."),
    USER_NOT_FOUND(404, "Not Found", "U002", "사용자를 찾을 수 없습니다."),
    PASSWORD_NOT_MATCHED(400, "Bad Request", "U003", "비밀번호가 일치하지 않습니다."),
    UNAUTHORIZED_USER(401, "Unauthorized", "U004", "인증되지 않은 사용자입니다."),

    // Store
    STORE_NOT_FOUND(404, "Not Found", "S001", "스토어를 찾을 수 없습니다."),
    ALREADY_CLOSED_STORE(400, "Bad Request", "S002", "이미 폐점된 가게입니다."),
    NO_AUTHORIZATION(403, "Forbidden", "S003", "이 가게에 대한 권한이 없습니다."),
    DUPLICATE_STORE_NAME(409, "Conflict", "S004", "이미 존재하는 가게 이름입니다."),
    DUPLICATE_STORE_NUMBER(409, "Conflict", "S005", "이미 존재하는 가게 전화번호입니다."),
    DUPLICATE_STORE_BIZNO(409, "Conflict", "S006", "이미 등록된 사업자 등록번호입니다."),

    // Review
    REVIEW_NOT_FOUND(404, "Not Found", "R001", "리뷰를 찾을 수 없습니다."),
    ALREADY_REVIEWED(400, "Bad Request", "R002", "이미 리뷰가 작성된 주문입니다."),
    USER_NOT_MATCHED(400, "Bad Request", "R003", "리뷰를 수정할 권한이 없습니다."),

    // Bookmark
    BOOKMARK_NOT_FOUND(404, "Not Found", "B001", "북마크를 찾을 수 없습니다."),
    ALREADY_BOOKMARKED(409, "Conflict", "B002", "이미 북마크된 가게입니다."),
    BOOKMARK_NOT_OWNER(403, "Forbidden", "B003", "해당 북마크에 대한 권한이 없습니다."),
    CANNOT_BOOKMARK_OWN_STORE(400, "Bad Request", "B004", "자신이 소유한 가게는 북마크할 수 없습니다."),

    // Report
    REPORT_NOT_FOUND(404, "Not Found", "RP001", "신고 내역을 찾을 수 없습니다."),
    ALREADY_REPORTED(409, "Conflict", "RP002", "이미 신고한 가게입니다."),
    INVALID_REPORT_REASON(400, "Bad Request", "RP003", "유효하지 않은 신고 사유입니다.");





    private final int status;
    private final String error;
    private final String code;
    private final String message;
}
