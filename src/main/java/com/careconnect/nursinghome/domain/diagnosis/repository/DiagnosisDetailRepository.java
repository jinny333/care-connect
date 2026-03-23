package com.careconnect.nursinghome.domain.diagnosis.repository;

import com.careconnect.nursinghome.domain.diagnosis.entity.DiagnosisDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosisDetailRepository extends JpaRepository<DiagnosisDetail, Long> {
}