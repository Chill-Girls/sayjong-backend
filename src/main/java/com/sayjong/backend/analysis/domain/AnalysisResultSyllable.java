package com.sayjong.backend.analysis.domain;

import com.sayjong.backend.lyric.domain.LyricSyllable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "analysis_result_syllable")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalysisResultSyllable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id; // ID(PK)

    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String feedbackText; //피드백 문구

    @Column(nullable = false)
    private Integer pronounceOk; //발음일치율

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "syl_no", nullable = false)
    private LyricSyllable lyricSyllable; //음절번호
}
