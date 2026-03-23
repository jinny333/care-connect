package com.careconnect.nursinghome.domain.diagnosis.service;

import com.careconnect.nursinghome.domain.diagnosis.entity.Diagnosis;
import com.careconnect.nursinghome.domain.diagnosis.entity.DiagnosisDetail;
import com.careconnect.nursinghome.domain.diagnosis.repository.DiagnosisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DiagnosisServiceImpl implements DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;

    @Override
    public Diagnosis create(Diagnosis diagnosis) {

        // 생성 시간 세팅
        diagnosis.setCreatedAt(LocalDateTime.now());

        // 🔥 핵심: detail에 부모 연결
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
}
