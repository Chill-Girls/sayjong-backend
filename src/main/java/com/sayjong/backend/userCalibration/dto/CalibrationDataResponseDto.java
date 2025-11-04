package com.sayjong.backend.userCalibration.dto;

import lombok.Getter;

@Getter
public class CalibrationDataResponseDto {

    private final String vowelTargetsJson; // target_vowels.json이 될 데이터

    private final String rawCalibrationJson; // vowel_calibration.json이 될 데이터

    public CalibrationDataResponseDto(String vowelTargetsJson, String rawCalibrationJson) {
        this.vowelTargetsJson = vowelTargetsJson;
        this.rawCalibrationJson = rawCalibrationJson;
    }
}
