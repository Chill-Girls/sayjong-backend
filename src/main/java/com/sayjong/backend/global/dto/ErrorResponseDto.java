package com.sayjong.backend.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

//에러 발생시 에러 응답용
@Getter
@AllArgsConstructor
public class ErrorResponseDto {
    private String message;
}
