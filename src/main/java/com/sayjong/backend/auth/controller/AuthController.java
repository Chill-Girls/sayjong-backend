package com.sayjong.backend.auth.controller;

import com.sayjong.backend.config.JwtTokenProvider;
import com.sayjong.backend.config.LogoutAccessTokenDenyList;
import com.sayjong.backend.auth.dto.TokenInfo;
import com.sayjong.backend.auth.dto.TokenRefreshRequestDto;
import com.sayjong.backend.auth.dto.UserLoginRequestDto;
import com.sayjong.backend.auth.dto.UserSignUpRequestDto;
import com.sayjong.backend.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final LogoutAccessTokenDenyList logoutAccessTokenDenyList;
    private final JwtTokenProvider jwtTokenProvider;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserSignUpRequestDto requestDto) {
        try {
            authService.registerUser(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).
                    body(Map.of("message", "회원가입이 성공적으로 완료되었습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto requestDto) {
        try {
            TokenInfo tokenInfo = authService.login(requestDto);
            return ResponseEntity.ok(tokenInfo);
        } catch (AuthenticationException e) {
            //이메일 또는 비밀번호가 틀린 경우
            log.warn("Login failed for loginId: {}", requestDto.getLoginId());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message","아이디 또는 비밀번호가 일치하지 않습니다."));
        }
    }

    //토큰 재발급
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody TokenRefreshRequestDto requestDto) {
        try {
            TokenInfo tokenInfo = authService.reissueToken(requestDto.getRefreshToken());
            return ResponseEntity.ok(tokenInfo);
        } catch (RuntimeException e) {
            //유효하지 않은 토큰이나 사용자를 찾을 수 없을 때
            log.warn("Token refresh failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveToken(request);

        if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
            logoutAccessTokenDenyList.add(accessToken);
        }

        return ResponseEntity.ok(Map.of("message", "성공적으로 로그아웃되었습니다."));
    }
}