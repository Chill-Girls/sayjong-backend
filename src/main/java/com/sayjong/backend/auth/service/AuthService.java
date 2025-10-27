package com.sayjong.backend.auth.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sayjong.backend.auth.dto.TokenInfo;
import com.sayjong.backend.auth.dto.UserLoginRequestDto;
import com.sayjong.backend.auth.dto.UserSignUpRequestDto;
import com.sayjong.backend.global.jwt.JwtTokenProvider;
import com.sayjong.backend.user.domain.User;
import com.sayjong.backend.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public User registerUser(UserSignUpRequestDto requestDto) { // 회원가입
        // 이메일 중복 확인
        if (userRepository.findByLoginId(requestDto.getLoginId()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder
                .encode(requestDto.getUserPassword());

        // 사용자 정보 생성
        User newUser = User.builder()
                .loginId(requestDto.getLoginId())
                .userPassword(encodedPassword) // 암호화된 비밀번호로 저장
                .nickname(requestDto.getNickname())
                .build();

        // 사용자 정보 저장
        return userRepository.save(newUser);
    }

    @Transactional
    public TokenInfo login(UserLoginRequestDto requestDto) { // 로그인
        // AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                requestDto.getLoginId(), requestDto.getUserPassword());

        // 비밀번호 일치 여부 검증
        Authentication authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken);

        // 검증된 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        // DB에서 사용자 정보를 찾아 Refresh Token저장
        User user = userRepository.findByLoginId(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "해당 아이디를 가진 사용자를 찾을 수 없습니다."));
        user.updateRefreshToken(tokenInfo.getRefreshToken());

        return tokenInfo;
    }

    @Transactional
    public TokenInfo reissueToken(String refreshToken) { // 토큰 재발급
        // 리프레시 토큰 유효성 검증
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("유효하지 않은 리프레시 토큰입니다.");
        }

        // DB에 저장된 토큰과 일치하는지 확인하여 사용자 정보 가져오기
        User user = userRepository.findByRefreshToken(refreshToken)
                .orElseThrow(
                        () -> new RuntimeException("리프레시 토큰에 해당하는 사용자가 없습니다."));

        // 사용자 정보를 바탕으로 새로운 인증 객체 생성
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getLoginId())
                .password("")
                .roles("USER")
                .build();
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities());

        // 새로운 토큰 생성
        TokenInfo newTokenInfo = jwtTokenProvider.generateToken(authentication);

        // DB에 새로운 리프레시 토큰으로 갱신
        user.updateRefreshToken(newTokenInfo.getRefreshToken());

        return newTokenInfo;
    }
}
