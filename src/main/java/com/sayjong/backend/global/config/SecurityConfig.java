package com.sayjong.backend.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sayjong.backend.global.jwt.JwtAuthenticationFilter;
import com.sayjong.backend.global.jwt.JwtTokenProvider;
import com.sayjong.backend.global.jwt.LogoutAccessTokenDenyList;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final LogoutAccessTokenDenyList logoutAccessTokenDenyList;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http
                // CSRF 보호 비활성화 (개발 초기 단계에서는 편리함을 위해 비활성화)
                .csrf(csrf -> csrf.disable())

                // HTTP Basic 인증 비활성화
                .httpBasic(httpBasic -> httpBasic.disable())

                // 세션 STATELESS 설정
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 요청 경로별 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/logout").authenticated()
                        .requestMatchers("/auth/**", "/songs").permitAll() // 해당
                                                                           // 경로는
                                                                           // 모두
                                                                           // 허용
                        .anyRequest().authenticated() // 나머지 모든 요청은 인증 필요
                )

                // JWT 인증 필터
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider,
                                logoutAccessTokenDenyList),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // 비밀번호 암호화
        return new BCryptPasswordEncoder();
    }
}
