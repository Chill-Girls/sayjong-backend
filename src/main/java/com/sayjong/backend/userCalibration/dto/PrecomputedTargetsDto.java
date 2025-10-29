package com.sayjong.backend.userCalibration.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class PrecomputedTargetsDto {
    private String calibratedAt;
    private String version;
    private Map<String, VowelTargetDto> vowels;
}
