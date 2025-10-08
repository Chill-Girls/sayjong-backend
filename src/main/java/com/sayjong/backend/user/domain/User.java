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
    private Integer userId;  //유저식별자(PK)

    @Column(nullable = false, length = 20)
    private String userPassword;  //유저비밀번호

    @Column(nullable = false, length = 50, unique = true)
    private String email;  //유저이메일

    @Column(nullable = false, length = 20)
    private String nickname;  //유저닉네임
}
