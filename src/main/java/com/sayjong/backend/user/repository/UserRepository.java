package com.sayjong.backend.user.repository;

import com.sayjong.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email); //이메일로 사용자를 찾음
    Optional<User> findByRefreshToken(String refreshToken); //리프레시 토큰으로 사용자를 찾음
}