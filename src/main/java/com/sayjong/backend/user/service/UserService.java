package com.sayjong.backend.user.service;

import com.sayjong.backend.config.JwtTokenProvider;
import com.sayjong.backend.user.domain.User;
import com.sayjong.backend.user.dto.TokenInfo;
import com.sayjong.backend.user.dto.UserLoginRequestDto;
import com.sayjong.backend.user.dto.UserSignUpRequestDto;
import com.sayjong.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public User registerUser(UserSignUpRequestDto requestDto) {  //회원가입
        //이메일 중복 확인
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getUserPassword());

        //사용자 정보 생성
        User newUser = User.builder()
                .email(requestDto.getEmail())
                .userPassword(encodedPassword) //암호화된 비밀번호로 저장
                .nickname(requestDto.getNickname())
                .build();

        //사용자 정보 저장
        return userRepository.save(newUser);
    }

    @Transactional
    public TokenInfo login(UserLoginRequestDto requestDto) { //로그인
        //AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getUserPassword());

        //비밀번호 일치 여부 검증
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //검증된 인증 정보를 기반으로 JWT 토큰 생성
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        return tokenInfo;
    }
}