package com.example.shoppingmall.domain.bookmark.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.shoppingmall.global.common.auth.jwt.JwtUtil;

public abstract class BaseController {
    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected JwtUtil jwtUtil;

    protected Long getCurrentUserId() {
        String token = getTokenFromHeader();
        return jwtUtil.getUserIdFromToken(token);
    }

    private String getTokenFromHeader() {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        throw new RuntimeException("Authorization 헤더가 없거나 잘못되었습니다.");
    }
}
