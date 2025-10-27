package com.sayjong.backend.auth.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sayjong.backend.auth.dto.TokenInfo;
import com.sayjong.backend.auth.dto.TokenRefreshRequestDto;
import com.sayjong.backend.auth.dto.UserLoginRequestDto;
import com.sayjong.backend.auth.dto.UserSignUpRequestDto;
import com.sayjong.backend.auth.service.AuthService;
import com.sayjong.backend.global.dto.MessageResponseDto;
import com.sayjong.backend.global.jwt.JwtTokenProvider;
import com.sayjong.backend.global.jwt.LogoutAccessTokenDenyList;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final LogoutAccessTokenDenyList logoutAccessTokenDenyList;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<MessageResponseDto> signUp(
            @RequestBody UserSignUpRequestDto requestDto) {
        // try-catch 제거
        authService.registerUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponseDto("회원가입이 성공적으로 완료되었습니다."));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenInfo> login(
            @RequestBody UserLoginRequestDto requestDto) {
        TokenInfo tokenInfo = authService.login(requestDto);
        return ResponseEntity.ok(tokenInfo);
    }

    // 토큰 재발급
    @PostMapping("/refresh")
    public ResponseEntity<TokenInfo> refresh(
            @RequestBody TokenRefreshRequestDto requestDto) {
        TokenInfo tokenInfo = authService
                .reissueToken(requestDto.getRefreshToken());
        return ResponseEntity.ok(tokenInfo);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<MessageResponseDto> logout(
            HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveToken(request);

        if (accessToken != null
                && jwtTokenProvider.validateToken(accessToken)) {
            logoutAccessTokenDenyList.add(accessToken);
        }

        return ResponseEntity.ok(new MessageResponseDto("성공적으로 로그아웃되었습니다."));
    }
}
