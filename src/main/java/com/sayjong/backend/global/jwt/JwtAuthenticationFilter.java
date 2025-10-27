package com.sayjong.backend.global.jwt;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final LogoutAccessTokenDenyList logoutAccessTokenDenyList;

    @Override
    // 요청이 들어올 때마다 JWT 토큰을 검사하여 인증을 처리
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 로그아웃된 토큰인지 확인
            if (logoutAccessTokenDenyList.contains(token)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "로그아웃된 토큰입니다.");
                return;
            }
            // 유효하고 로그아웃되지 않은 토큰이면 인증 정보 저장
            Authentication authentication = jwtTokenProvider
                    .getAuthentication(token);
            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

}
