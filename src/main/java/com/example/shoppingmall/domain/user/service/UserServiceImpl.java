package com.example.shoppingmall.domain.user.service;

import com.example.shoppingmall.domain.user.dto.request.SignUpRequestDto;
import com.example.shoppingmall.domain.user.dto.response.SignUpResponseDto;
import com.example.shoppingmall.domain.user.entity.User;
import com.example.shoppingmall.domain.user.repository.UserRepository;
import com.example.shoppingmall.global.common.config.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override // 회원가입
    public SignUpResponseDto signUp(SignUpRequestDto dto) {

        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        //객체에 넣어주고
        User user = new User(dto.getNickname(), dto.getEmail(), encodedPassword, dto.getAddress(), dto.getUserAuthority());

        //DB에 넣어준다.
        User savedUser = userRepository.save(user);

        //DB에 있는 데이터를 커내서
        return new SignUpResponseDto(
                savedUser.getNickName()
                , savedUser.getEmail()
                , savedUser.getAddress()
                , savedUser.getUserAuthority()
                , savedUser.getUpdatedAt());
    }


}
