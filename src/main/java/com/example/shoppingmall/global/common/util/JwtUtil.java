package com.example.shoppingmall.global.common.util;

import com.example.shoppingmall.domain.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

@Component
public class JwtUtil {

    //자바에서 제공하는 SecretKey 이다.
    private final SecretKey secretKey;

    //외부 설정 파일(application.properties, application.yml 등)에 정의된 값을
    //필드에 주입해주는 기능을 합니다.
    @Value("${jwt.access-token-expiration}")
    private Long accessExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private Long refreshExpiration;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    /*
     * Access Token 을 만들어주는 메서드이다.
     * 사용자가 인증(로그인)했다는 것을 증명하는 하나의 전자서명 같은것이라 보면 된다.
     * */
    public String createAccessToken(User user) {
        return Jwts.builder()
                //토큰안에 사용자의 이메일 정보를 기록한다.
                .claim("email", user.getEmail())
                //토큰이 발급된 시각을 기록합니다.
                .issuedAt(new Date(System.currentTimeMillis()))

                //토큰이 언제까지 쓸 수 있는지 만료시간을 정한다.
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))

                //비밀키로 서명한다. 위조를 막기 위해서, 꼭 비밀 도장처럼 서명해 해야함
                .signWith(secretKey)

                // 위 정보를 다 묶어서 하나의 문자열로 완성한다.
                .compact();
    }

    /*
    * RefreshToken을 만들어주는 함수 이다.
    * Aceess Token이 만료 되었을 때 다시 로그인하지 않고도 새 액세스 토큰을 발급받을 수 있게 해주는 토근이다.
    * */
    public String createRefreshToken(User user) {

        return Jwts.builder()
                // 토큰안에 사용자 이메일 정보를 담음
                .claim("email", user.getEmail())

                //이 토큰이 언제 만료되는지(언제까지 쓸수 있는지) 설정
                .expiration(new Date(System.currentTimeMillis() + refreshExpiration))

                // Refresh Token에 issuedAt이 없는 이유
                //-->Refresh Token에서는 대부분의 경우 발급 시간까지 굳이 포함하지 않아도,만료 시간(expiration)만으로도 충분히 토큰의 유효성을 관리할 수 있기 때문입니다.
                //비밀키로 서명
                .signWith(secretKey)
                //모든 정보를 모아서 하나의 토큰으로 만듦
                .compact();
    }

    /*
    * RefreshToken이 올바른 토큰인지 확인하는 메서드이다.
    * 유요한 토큰이면 true를 반환하고
    * 만료되었거나 잘못된 토큰이라면 false로 반환한다.*/
    public boolean isvalidRefreshToken(String refreshToken) {
        try {
            //Refresh Token에서 정보를 추출하는 함수
            getClaimsToken(refreshToken);
            //유요한 토큰일 경우 true 반환
            return true;
        } catch (NullPointerException | JwtException e) {
            return false;
        }
    }

    /*
    token을 입력받고 claims라는 데이터를 추출한다.
    Claims란?
    → JWT 내부에 저장된 사용자 데이터, 토큰의 만료 시간 등과 같은 정보를 뜻한다.
    * */
    private Claims getClaimsToken(String token) {

        //JWT를 파싱(풀이해석)하기위한 빌더를 생성
        return Jwts.parser()

                //JWT 서명을 검증하기 위해 사용한다.
                //입력받은 토큰이 위조되지 않았는지, 서명이 유요한지를 확인한다.
                .verifyWith(secretKey)

                //준비된 파서를 빌드한다.
                .build()

                //입력받은 JWT토큰을 분석해 서명된 페이로드를 반환한다.
                .parseSignedClaims(token)

                //Claims 객체에서 실제 데이터를 추출한다.
                .getPayload();
    }

}
