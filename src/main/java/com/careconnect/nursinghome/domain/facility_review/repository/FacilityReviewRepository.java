package com.careconnect.nursinghome.domain.facility_review.repository;

import com.careconnect.nursinghome.domain.facility_review.entity.FacilityReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacilityReviewRepository extends JpaRepository<FacilityReview, Long> {

    List<FacilityReview> findByFacilityId(Long facilityId);
}