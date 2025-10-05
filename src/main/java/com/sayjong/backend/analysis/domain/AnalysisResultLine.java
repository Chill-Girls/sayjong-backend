package com.sayjong.backend.analysis.domain;

import com.sayjong.backend.lyric.domain.LyricLine;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "analysis_result_line")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalysisResultLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id; //ID(PK)

    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String feedbackText; //피드백 문구

    @Column(columnDefinition = "JSON")
    private String wrongSegments; //오발음 구간 배열(JSON 문자열로 저장)

    @Column(nullable = false)
    private Integer pronounceOk; //발음일치율

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_no", nullable = false)
    private LyricLine lyricLine; //소절번호
}
