package com.careconnect.nursinghome.domain.facility_review.service;

import com.careconnect.nursinghome.domain.facility_review.dto.*;
import java.util.List;

public interface FacilityReviewService {

    FacilityReviewResponseDto create(FacilityReviewRequestDto dto);

    FacilityReviewResponseDto get(Long id);

    List<FacilityReviewResponseDto> getByFacility(Long facilityId);

    void update(Long id, FacilityReviewRequestDto dto);

    void delete(Long id);

    void updateAdminReply(Long id, String reply);
}
