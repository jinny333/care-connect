package com.careconnect.nursinghome.domain.diagnosis.service;

import com.careconnect.nursinghome.domain.diagnosis.entity.Diagnosis;

import java.util.List;

public interface DiagnosisService {
    Diagnosis create(Diagnosis diagnosis);
    Diagnosis getById(Long id);
    List<Diagnosis> getByMember(Long memberId);  // 추가
    void update(Long id, Diagnosis diagnosis);    // 추가
}