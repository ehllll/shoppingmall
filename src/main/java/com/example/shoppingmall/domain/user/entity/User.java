package com.example.shoppingmall.domain.user.entity;

import com.example.shoppingmall.global.common.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nickName;

	@Column(name="user_name",nullable = false)
	private String userName;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String address;

	@Enumerated(EnumType.STRING)
	@Column(name = "authority", nullable = false)
	private UserRole userAuthority;

	public User(String nickName,String userName, String password, String address, UserRole userAuthority) {
		this.nickName = nickName;
		this.userName = userName;
		this.password = password;
		this.address = address;
		this.userAuthority = userAuthority;
	}

	public void updatePassword(String newPassword) {
		this.password = newPassword;
	}
}
