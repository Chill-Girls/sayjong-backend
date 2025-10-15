package com.sayjong.backend.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRefreshRequestDto {
    private String refreshToken;
}