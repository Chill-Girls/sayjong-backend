package com.sayjong.backend.auth.service;

import com.sayjong.backend.user.domain.User;
import com.sayjong.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    //사용자가 입력한 아이디를 가진 User 객체가 실제로 존재하는지 확인
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLoginId(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
    }

    //User 객체를 스프링 시큐리티가 사용하는 표준 사용자 정보 형식으로 변환
    private UserDetails createUserDetails(User user) {
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLoginId())
                .password(user.getUserPassword())
                .roles("USER") //기본 역할 부여
                .build();
    }
}