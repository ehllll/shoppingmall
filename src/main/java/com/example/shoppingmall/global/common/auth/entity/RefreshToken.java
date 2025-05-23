package com.example.shoppingmall.global.common.auth.entity;

import com.example.shoppingmall.domain.user.entity.User;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

//리프레쉬 토큰을 엔티티로 만들어 사용자와 맵핑해준다.
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
public class RefreshToken {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String token;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public RefreshToken(User user, String token) {
        this.token = token;
        this.user = user;
    }

    public void updateRefreshToken(String token) {
        this.token = token;
    }
}
