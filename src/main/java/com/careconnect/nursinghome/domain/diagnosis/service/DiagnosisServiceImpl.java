package com.careconnect.nursinghome.domain.diagnosis.service;

import com.careconnect.nursinghome.domain.diagnosis.entity.Diagnosis;
import com.careconnect.nursinghome.domain.diagnosis.entity.DiagnosisDetail;
import com.careconnect.nursinghome.domain.diagnosis.repository.DiagnosisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiagnosisServiceImpl implements DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;

    @Override
    public Diagnosis create(Diagnosis diagnosis) {
        diagnosis.setCreatedAt(LocalDateTime.now());
        diagnosis.setDiagnosisDate(LocalDateTime.now()); // 추가

        if (diagnosis.getDetails() != null) {
            for (DiagnosisDetail detail : diagnosis.getDetails()) {
                detail.setDiagnosis(diagnosis);
            }
        }

        return diagnosisRepository.save(diagnosis);
    }

    @Override
    public Diagnosis getById(Long id) {
        return diagnosisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diagnosis not found"));
    }

    @Override
    public List<Diagnosis> getByMember(Long memberId) {
        return diagnosisRepository.findByMemberId(memberId);
    }

    @Override
    public void update(Long id, Diagnosis diagnosis) {
        Diagnosis existing = diagnosisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diagnosis not found"));
        existing.setRiskResult(diagnosis.getRiskResult());
        existing.setTotalScore(diagnosis.getTotalScore());
        diagnosisRepository.save(existing);
    }
}
