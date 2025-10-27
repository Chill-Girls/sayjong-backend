package com.sayjong.backend.user.domain;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer userId; // 유저식별자(PK)

    @Column(nullable = false)
    private String userPassword; // 유저비밀번호

    @Column(nullable = false, length = 50, unique = true)
    private String loginId; // 로그인아이디

    @Column(nullable = false, length = 20)
    private String nickname; // 유저닉네임

    @Column
    private String refreshToken; // 리프레시 토큰

    // 새로운 리프레시 토큰으로 갱신
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
