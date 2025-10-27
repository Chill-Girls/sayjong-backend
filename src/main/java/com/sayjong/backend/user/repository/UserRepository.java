package com.sayjong.backend.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sayjong.backend.user.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByLoginId(String loginId); // 아이디로 사용자를 찾음

    Optional<User> findByRefreshToken(String refreshToken); // 리프레시 토큰으로 사용자를 찾음
}
