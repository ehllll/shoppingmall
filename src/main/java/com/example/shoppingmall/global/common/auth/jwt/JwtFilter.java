package com.example.shoppingmall.global.common.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private static final String[] WHITE_LIST = {"/users/signup", "/auths/login", "/auths/logout"};

    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        log.debug("요청 URI: {}", requestURI);
        log.debug("isWhiteList: {}", isWhiteList(requestURI));
        if (isWhiteList(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        //jwt토큰을 추출한다.
        String jwt = jwtUtil.extractToken(httpRequest);
        log.debug("추출된 JWT: {}", jwt); // null인지 확인
        String extractTokon = jwtUtil.extractToken(httpRequest);


        //추출한 jwt값이 비어있지 않는지(null,공백이 아닌지)확인
        //비어있으면 false

        if (jwt != null && !jwt.isBlank() && jwtUtil.isvalidRefreshToken(jwt)) {


            UserAuth userAuth = jwtUtil.extractUserAuth(extractTokon);

            List<SimpleGrantedAuthority> authorities = List.of(
                    new SimpleGrantedAuthority("ROLE_" + userAuth.getRole().name())
            );

            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(userAuth, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(token);

        } else {

            log.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
        }

        filterChain.doFilter(request, response);
    }

    private boolean isWhiteList(String requestURL) {

        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURL);
    }
}
