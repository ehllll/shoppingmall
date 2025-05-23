package com.example.shoppingmall.domain.user.repository;

import com.example.shoppingmall.global.common.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


//토큰을 통해 유저를 조회하기 위해 repository도 만들었다.
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUserId(Long userId);


    Optional<RefreshToken> findByRefreshToken(String refreshToken);


}
