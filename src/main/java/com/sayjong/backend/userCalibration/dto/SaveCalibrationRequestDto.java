package com.sayjong.backend.userCalibration.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class SaveCalibrationRequestDto {
    private PrecomputedTargetsDto precomputedData;
    private Map<String, RawFrameDto> rawData;
}
