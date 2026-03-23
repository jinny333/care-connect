package com.careconnect.nursinghome.domain.diagnosis.service;

import com.careconnect.nursinghome.domain.diagnosis.entity.Diagnosis;

public interface DiagnosisService {

    Diagnosis create(Diagnosis diagnosis);

    Diagnosis getById(Long id);
}