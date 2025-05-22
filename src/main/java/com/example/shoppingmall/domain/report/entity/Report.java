package com.example.shoppingmall.domain.report.entity;

import com.example.shoppingmall.domain.store.entity.Store;
import com.example.shoppingmall.domain.user.entity.User;
import com.example.shoppingmall.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "report")
@NoArgsConstructor
public class Report extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;


    // 신고생성
    public Report(User user,Store store, String reason) {
        this.store = store;
        this.user = user;
        this.reason = reason;
    }
}
