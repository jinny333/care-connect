package com.careconnect.nursinghome.domain.diagnosis.repository;

import com.careconnect.nursinghome.domain.diagnosis.entity.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

}