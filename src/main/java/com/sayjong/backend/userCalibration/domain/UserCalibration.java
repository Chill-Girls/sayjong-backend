package com.sayjong.backend.userCalibration.domain;

import com.sayjong.backend.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class UserCalibration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    // 정답 좌표
    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String vowelTargetsJson;

    // calibration 좌표
    @Lob
    @Column(columnDefinition = "MEDIUMTEXT")
    private String rawCalibrationJson;

    // 좌표가 저장된 시간 (UTC)
    private Instant calibratedAt;

    // 연동 후 데이터(json) 형식이 바뀌었을 때 저장되어 있는 데이터가 어떤 형식 기준으로 저장된 것인지 확인할 수 있도록 넣어줌
    private String dataVersion;
}
