package com.example.shoppingmall.domain.user.repository;

import com.example.shoppingmall.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String username);

    boolean existsByUserName(String username);



}
