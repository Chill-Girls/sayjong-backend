package com.sayjong.backend.userCalibration.controller;

import com.sayjong.backend.userCalibration.dto.CalibrationDataResponseDto;
import com.sayjong.backend.userCalibration.dto.SaveCalibrationRequestDto;
import com.sayjong.backend.userCalibration.service.CalibrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calibration")
@RequiredArgsConstructor
public class CalibrationController {

    private final CalibrationService calibrationService;

    // 로그인한 사용자의 캘리브레이션 좌표를 서버 데이터베이스에 저장하는 api + 프론트에 반환
    @PostMapping("/targets")
    public ResponseEntity<CalibrationDataResponseDto> saveCalibrationTargets(
            @RequestBody SaveCalibrationRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        CalibrationDataResponseDto responseDto = calibrationService.saveCalibrationData(
                userDetails.getUsername(),
                requestDto
        );

        return ResponseEntity.ok(responseDto);
    }
    // 로그인한 사용자의 캘리브레이션 좌표를 조회하는 api (존재한다면 반환)
    @GetMapping("my-data")
    public ResponseEntity<CalibrationDataResponseDto> getMyCalibrationData(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        CalibrationDataResponseDto responseDto = calibrationService.getCalibrationData(userDetails.getUsername());

        return ResponseEntity.ok(responseDto);
    }
}