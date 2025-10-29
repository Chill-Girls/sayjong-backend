package com.sayjong.backend.userCalibration.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class RawFrameDto {
    private Map<String, List<Double>> landmarks;
    private Map<String, Double> blendshapes;
}
