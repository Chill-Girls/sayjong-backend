package com.sayjong.backend.user.dto;

import com.sayjong.backend.user.domain.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyPageResponseDto {

    private Integer userId;
    private String loginId;
    private String nickname;

    public static MyPageResponseDto from(User user) {
        return MyPageResponseDto.builder()
                .userId(user.getUserId())
                .loginId(user.getLoginId())
                .nickname(user.getNickname())
                .build();
    }
}
