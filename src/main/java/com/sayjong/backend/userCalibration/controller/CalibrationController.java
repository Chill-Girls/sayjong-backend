package com.sayjong.backend.userCalibration.controller;

import com.sayjong.backend.userCalibration.dto.SaveCalibrationRequestDto;
import com.sayjong.backend.userCalibration.service.CalibrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calibration")
@RequiredArgsConstructor
public class CalibrationController {

    private final CalibrationService calibrationService;

    // 로그인한 사용자의 캘리브레이션 좌표를 서버 데이터베이스에 저장하는 api
    @PostMapping("/targets")
    public ResponseEntity<Void> saveCalibrationTargets(
            @RequestBody SaveCalibrationRequestDto requestDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        if (userDetails == null) {
            return ResponseEntity.status(401).build(); // Unauthorized
        }

        calibrationService.saveCalibrationData(userDetails.getUsername(), requestDto);

        return ResponseEntity.ok().build();
    }
}