package com.sayjong.backend.userCalibration.repository;

import com.sayjong.backend.user.domain.User;
import com.sayjong.backend.userCalibration.domain.UserCalibration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCalibrationRepository extends JpaRepository<UserCalibration, Long> {
    // 사용자로 캘리브레이션 데이터 찾기
    Optional<UserCalibration> findByUser(User user);
}