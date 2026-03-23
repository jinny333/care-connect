package com.careconnect.nursinghome.domain.diagnosis.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiagnosisDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔥 부모 (Diagnosis)
    @ManyToOne
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;

    // 문제 번호
    private Integer questionNumber;

    // 사용자 답변
    private String answer;

    // 문제 내용
    private String questionContent;
}