package com.sayjong.backend.analysis.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import com.sayjong.backend.lyric.domain.LyricLine;

import lombok.*;

@Entity
@Table(name = "analysis_result_line")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AnalysisResultLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id; // ID(PK)

    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String feedbackText; // 피드백 문구

    @OneToMany(mappedBy = "analysisResultLine", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<WrongSegment> wrongSegments = new ArrayList<>(); // 오발음구간(별도의
                                                                  // 테이블로 분리하여
                                                                  // 관리)

    @Column(nullable = false)
    private Integer pronounceOk; // 발음일치율

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_no", nullable = false)
    private LyricLine lyricLine; // 소절번호
}
