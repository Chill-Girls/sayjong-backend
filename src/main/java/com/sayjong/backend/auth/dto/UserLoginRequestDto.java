package com.sayjong.backend.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDto {
    private String loginId;
    private String userPassword;
}