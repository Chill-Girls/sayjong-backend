package com.sayjong.backend.userCalibration.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sayjong.backend.user.domain.User;
import com.sayjong.backend.user.repository.UserRepository;
import com.sayjong.backend.userCalibration.domain.UserCalibration;
import com.sayjong.backend.userCalibration.dto.CalibrationDataResponseDto;
import com.sayjong.backend.userCalibration.dto.PrecomputedTargetsDto;
import com.sayjong.backend.userCalibration.dto.RawFrameDto;
import com.sayjong.backend.userCalibration.dto.SaveCalibrationRequestDto;
import com.sayjong.backend.userCalibration.repository.UserCalibrationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CalibrationService {

    private final UserCalibrationRepository calibrationRepo;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public CalibrationDataResponseDto saveCalibrationData(String loginId, SaveCalibrationRequestDto requestDto) {

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        try {
            // DTO 에서 두 데이터를 분리
            PrecomputedTargetsDto precomputedDto = requestDto.getPrecomputedData();
            Map<String, RawFrameDto> rawDataMap = requestDto.getRawData();

            if (precomputedDto == null) {
                throw new IllegalArgumentException("'precomputedData'가 null입니다. JSON 키 이름을 확인하세요.");
            }
            if (rawDataMap == null) {
                throw new IllegalArgumentException("'rawData'가 null입니다. JSON 키 이름을 확인하세요.");
            }
            if (precomputedDto.getCalibratedAt() == null) {
                throw new IllegalArgumentException("'calibratedAt' 필드가 null입니다. DTO 매핑을 확인하세요.");
            }

            // 정답좌표 데이터를 JSON 문자열로 변환
            String vowelsJson = objectMapper.writeValueAsString(precomputedDto.getVowels());

            // calibration 데이터를 JSON 문자열로 변환
            String rawJson = objectMapper.writeValueAsString(rawDataMap);

            // 기존 데이터가 있는지 확인
            UserCalibration calibration = calibrationRepo.findByUser(user)
                    .orElse(new UserCalibration());

            // 데이터 설정
            calibration.setUser(user);
            calibration.setVowelTargetsJson(vowelsJson); // 정답좌표
            calibration.setRawCalibrationJson(rawJson);  // calibration 좌표

            calibration.setCalibratedAt(Instant.parse(precomputedDto.getCalibratedAt()));
            calibration.setDataVersion(precomputedDto.getVersion());

            // save 후 반환받기
            UserCalibration savedCalibration = calibrationRepo.save(calibration);

            return new CalibrationDataResponseDto(
                    savedCalibration.getVowelTargetsJson(),
                    savedCalibration.getRawCalibrationJson()
            );

        } catch (JsonProcessingException e) {
            throw new RuntimeException("캘리브레이션 데이터 JSON 직렬화에 실패했습니다.", e);
        }
    }

    // calibration 데이터를 조회하는 메서드
    @Transactional(readOnly = true)
    public CalibrationDataResponseDto getCalibrationData(String loginId) {

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() ->new EntityNotFoundException("사용자를 찾을 수 없습니다"));

        UserCalibration calibration = calibrationRepo.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("저장된 캘리브레이션 데이터가 없습니다"));

        return new CalibrationDataResponseDto(
                calibration.getVowelTargetsJson(),
                calibration.getRawCalibrationJson()
        );
    }
}