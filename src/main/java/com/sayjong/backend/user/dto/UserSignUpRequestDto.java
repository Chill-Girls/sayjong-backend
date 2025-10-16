package com.sayjong.backend.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignUpRequestDto {
    private String loginId;
    private String userPassword;
    private String nickname;
}