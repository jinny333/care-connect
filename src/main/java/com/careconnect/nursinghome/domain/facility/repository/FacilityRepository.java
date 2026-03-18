package com.careconnect.nursinghome.domain.facility.repository;

import com.careconnect.nursinghome.domain.facility.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
}