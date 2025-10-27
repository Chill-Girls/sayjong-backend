package com.sayjong.backend.analysis.domain;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "wrong_segment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class WrongSegment { // 오발음구간 (별도의 테이블로 분리하여 관리)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id; // ID(PK)

    @Column(nullable = false)
    private Integer segmentIndex; // 오발음된 음절의 인덱스

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private AnalysisResultLine analysisResultLine;

}
