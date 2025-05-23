package com.example.shoppingmall.domain.user.service;

import com.example.shoppingmall.domain.user.dto.request.SignUpRequestDto;
import com.example.shoppingmall.domain.user.dto.request.UpdatePasswordRequestDto;
import com.example.shoppingmall.domain.user.dto.response.SignUpResponseDto;
import com.example.shoppingmall.global.common.auth.dto.response.TokenResponse;
import com.example.shoppingmall.global.common.auth.entity.RefreshToken;
import com.example.shoppingmall.domain.user.entity.User;
import com.example.shoppingmall.domain.user.repository.RefreshTokenRepository;
import com.example.shoppingmall.domain.user.repository.UserRepository;
import com.example.shoppingmall.global.common.auth.jwt.JwtUtil;
import com.example.shoppingmall.global.common.config.PasswordEncoder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override // 회원가입
    public SignUpResponseDto signUp(SignUpRequestDto requestDto) {

        //아이디가 존재하면?-->예외처리  (repository단에서 메서드를 하나 만들어서 사용한다.)
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다.");
        }

        //요청해온 비밀번호 암호화 시킨다.
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        //객체에 넣어준다.
        User user = new User(requestDto.getNickname()
                , requestDto.getUsername()
                , encodedPassword
                , requestDto.getAddress()
                , requestDto.getUserAuthority());

        //user 정보를 DB에 넣어준다.
        User saveUser = userRepository.save(user);

        //DB에 있는 데이터를 꺼내서
        return new SignUpResponseDto(saveUser.getNickName(),saveUser.getUsername(),saveUser.getAddress(),saveUser.getUserAuthority(), LocalDateTime.now());
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
}
