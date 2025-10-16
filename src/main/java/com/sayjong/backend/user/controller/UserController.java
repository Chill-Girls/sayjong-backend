package com.sayjong.backend.user.controller;

import com.sayjong.backend.user.dto.TokenInfo;
import com.sayjong.backend.user.dto.TokenRefreshRequestDto;
import com.sayjong.backend.user.dto.UserLoginRequestDto;
import com.sayjong.backend.user.dto.UserSignUpRequestDto;
import com.sayjong.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserSignUpRequestDto requestDto) {
        try {
            userService.registerUser(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 성공적으로 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestDto requestDto) {
        try {
            TokenInfo tokenInfo = userService.login(requestDto);
            return ResponseEntity.ok(tokenInfo);
        } catch (AuthenticationException e) {
            //이메일 또는 비밀번호가 틀린 경우
            log.warn("Login failed for loginId: {}", requestDto.getLoginId());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 일치하지 않습니다.");
        }
    }

    //토큰 재발급
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody TokenRefreshRequestDto requestDto) {
        try {
            TokenInfo tokenInfo = userService.reissueToken(requestDto.getRefreshToken());
            return ResponseEntity.ok(tokenInfo);
        } catch (RuntimeException e) {
            //유효하지 않은 토큰이나 사용자를 찾을 수 없을 때
            log.warn("Token refresh failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}