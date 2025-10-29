package com.sayjong.backend.userCalibration.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class VowelTargetDto {
    private Map<String, LandmarkDto> landmarks;
    private Map<String, Double> blendshapes;
}
