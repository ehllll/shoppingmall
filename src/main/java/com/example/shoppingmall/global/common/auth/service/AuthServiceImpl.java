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

        //accessToken, refreshToken 을 발급한다.
        String accessToken = jwtUtil.createAccessToken(user);
        String refreshToken = jwtUtil.createRefreshToken(user);

        //생성한 RefreshToken을 객체에 넣어준다.
        RefreshToken newRefreshToken = new RefreshToken(user, refreshToken);

        //RefreshToken을  DB에 넣어준다.
        refreshTokenRepository.save(newRefreshToken);

        //새로 생성된 Access Token과 Refresh Token을 응답 객체에 담아서 반환
        return new TokenResponse(accessToken, refreshToken);
    }

    @Override
    public void logout(String refreshToken) {

        RefreshToken findRefreshToken = refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new IllegalArgumentException("로그아웃 되었습니다"));

        refreshTokenRepository.delete(findRefreshToken);
    }
}
