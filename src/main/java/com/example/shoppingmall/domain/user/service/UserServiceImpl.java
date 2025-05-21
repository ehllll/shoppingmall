package com.example.shoppingmall.domain.user.service;

import com.example.shoppingmall.domain.user.dto.request.SignUpRequestDto;
import com.example.shoppingmall.domain.user.dto.request.SignInRequestDto;
import com.example.shoppingmall.domain.user.dto.request.UpdatePasswordRequestDto;
import com.example.shoppingmall.domain.user.dto.response.TokenResponse;
import com.example.shoppingmall.domain.user.entity.RefreshToken;
import com.example.shoppingmall.domain.user.entity.User;
import com.example.shoppingmall.domain.user.repository.RefreshTokenRepository;
import com.example.shoppingmall.domain.user.repository.UserRepository;
import com.example.shoppingmall.global.common.util.JwtUtil;
import com.example.shoppingmall.global.common.config.PasswordEncoder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override // 회원가입
    public TokenResponse signUp(SignUpRequestDto requestDto) {

        //비밀번호가 존재하면?-->예외처리  (repository단에서 메서드를 하나 만들어서 사용한다.)
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다.");
        }

        //요청해온 비밀번호 암호화 시킨다.
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        //객체에 넣어준다.
        User user = new User(requestDto.getNickname()
                , requestDto.getUsername()
                , encodedPassword
                , requestDto.getAddress()
                , requestDto.getUserAuthority());

        //accessToken, refreshToken 을 발급한다.
        String accessToken = jwtUtil.createAccessToken(user);
        String refreshToken = jwtUtil.createRefreshToken(user);

        //생성한 RefreshToken을 객체에 넣어준다.
        RefreshToken newRefreshToken = new RefreshToken(user, refreshToken);

        //user 정보를 DB에 넣어준다.
        userRepository.save(user);

        //RefreshToken을  DB에 넣어준다.
        refreshTokenRepository.save(newRefreshToken);

        //DB에 있는 데이터를 꺼내서
        return new TokenResponse(accessToken, refreshToken);
    }

    @Transactional
    @Override
    public void updatePassword(Long id, UpdatePasswordRequestDto requestDto) {

        //유저가 있는지 확인한다.
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!user.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //기존 비밀번호와 내가 새로 바꿀 비밀번호가 같은지 확인한다.
        if (passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 같습니다.");
        }
        user.updatePassword(requestDto.getPassword());
    }

    @Transactional
    @Override
    public TokenResponse signIn(SignInRequestDto requestDto) {

        //유저가 있는지 찾는다
        User user = userRepository.findByUsername(requestDto.username()).orElseThrow(() -> new IllegalArgumentException("존재하지 않은 사용자입니다."));

        //만약 요청한 비밀번호와 유져의 비밀번호가 같지 않다면? 예외처리를 한다.
        if (!passwordEncoder.matches(requestDto.password(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        //유저가 있으면 getId()를 통해 토큰이 있는지 확인한다.
        RefreshToken refreshTokenEntity = refreshTokenRepository.findByUserId(user.getId()).orElseThrow(() -> new IllegalArgumentException("토큰이 존재하지 않습니다."));

        //Access Token을 초기값으로 빈 문자열을 만든다.


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
