package com.careconnect.nursinghome.domain.diagnosis.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private Integer totalScore;

    private String riskResult;

    private LocalDateTime diagnosisDate;

    private LocalDateTime createdAt;

    // 🔥 핵심 (1:N)
    @OneToMany(mappedBy = "diagnosis", cascade = CascadeType.ALL)
    private List<DiagnosisDetail> details = new ArrayList<>();

    // 🔥 핵심 메서드
    public void addDetail(DiagnosisDetail detail) {
        details.add(detail);
        detail.setDiagnosis(this);
    }
}