package com.sayjong.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			// CSRF 보호 비활성화 (개발 초기 단계에서는 편리함을 위해 비활성화)
			.csrf(csrf -> csrf.disable())

			// 모든 HTTP 요청에 대한 접근 허용 설정
			.authorizeHttpRequests(auth -> auth
				// 모든 요청(anyRequest)을 허용(permitAll)
				.anyRequest().permitAll()
			);

		return http.build();
	}
}
