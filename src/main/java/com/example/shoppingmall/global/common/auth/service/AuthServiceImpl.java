package com.example.shoppingmall.global.common.auth.service;

import com.example.shoppingmall.domain.user.entity.UserRole;
import com.example.shoppingmall.global.common.auth.dto.request.SignInRequestDto;
import com.example.shoppingmall.global.common.auth.dto.response.TokenResponse;
import com.example.shoppingmall.global.common.auth.entity.RefreshToken;
import com.example.shoppingmall.domain.user.entity.User;
import com.example.shoppingmall.domain.user.repository.RefreshTokenRepository;
import com.example.shoppingmall.domain.user.repository.UserRepository;
import com.example.shoppingmall.global.common.config.PasswordEncoder;
import com.example.shoppingmall.global.common.auth.jwt.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    @Override
    public TokenResponse signIn(SignInRequestDto requestDto) {

        //유저가 있는지 찾는다
        User user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(() -> new IllegalArgumentException("존재하지 않은 사용자입니다."));

        //만약 요청한 비밀번호와 유져의 비밀번호가 같지 않다면? 예외처리를 한다.
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        //유저가 있으면 getId()를 통해 토큰이 있는지 확인한다.
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByUserId(user.getId()).orElseThrow(() -> new IllegalArgumentException("토큰이 존재하지 않습니다."));


        // 굳이 if 문을 할 필요가 없다 . 이유는 ? --> 유효성에 상관없이 항상 새로운 Refresh Token을 생성하기 때문이다.
//        //만약 refreshToken이 유효하다면?
//        if (jwtUtil.isvalidRefreshToken(refreshToken)) {
//
//            // 새로운 accessToken을 생성
//            accessToken = jwtUtil.createAccessToken(user);
//
//            // Access Token과 Refresh Token을 반환 (로그인 성공)
//            return new TokenResponse(accessToken, refreshToken);
//
//        } else {//만약 그렇지 않다면 ?

        // 새로운 Refresh Token 을 생성한다.
        String refreshToken = jwtUtil.createRefreshToken(user);


        // RefreshToken 엔티티 객체를 업데이트 (새로운 Refresh Token 값 저장)
        RefreshToken newRefreshToken = new RefreshToken(user, refreshToken);

        //DB에 새로운 Refresh Token 저장한다.
        refreshTokenRepository.save(newRefreshToken);

        //accessToken도 생성한다.
        String accessToken = jwtUtil.createAccessToken(user);


        //새로 생성된 Access Token과 Refresh Token을 응답 객체에 담아서 반환
        return new TokenResponse(accessToken, refreshToken);
    }
    @Override
    public void logout(String refreshToken) {

        RefreshToken findRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new IllegalArgumentException("로그아웃 되었습니다"));

        refreshTokenRepository.delete(findRefreshToken);
    }
}
