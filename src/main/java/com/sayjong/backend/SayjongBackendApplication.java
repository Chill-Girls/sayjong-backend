package com.sayjong.backend;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class SayjongBackendApplication {

    // 애플리케이션 기본 시간대를 한국으로 설정
    @PostConstruct
    public void setKstTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    public static void main(String[] args) {
        SpringApplication.run(SayjongBackendApplication.class, args);
    }

}
