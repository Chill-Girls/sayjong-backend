package com.sayjong.backend.user.service;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sayjong.backend.user.domain.User;
import com.sayjong.backend.user.dto.MyPageResponseDto;
import com.sayjong.backend.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    // loginId를 사용해 User를 조회하고 DTO로 변환
    public MyPageResponseDto getMyPageInfo(String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(
                        () -> new EntityNotFoundException("사용자를 찾을 수 없습니다ㅏ"));

        return MyPageResponseDto.from(user);
    }

    @Transactional
    public void deleteUSer(String loginId) {
        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(
                        () -> new EntityNotFoundException("사용자를 찾을 수 없습니다"));

        userRepository.delete(user);
    }
}
