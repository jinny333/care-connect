package com.careconnect.nursinghome.domain.facility_review.service;

import com.careconnect.nursinghome.domain.facility_review.dto.*;
import com.careconnect.nursinghome.domain.facility_review.entity.FacilityReview;
import com.careconnect.nursinghome.domain.facility_review.repository.FacilityReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacilityReviewServiceImpl implements FacilityReviewService {

    private final FacilityReviewRepository repository;

    @Override
    public FacilityReviewResponseDto create(FacilityReviewRequestDto dto) {

        FacilityReview review = FacilityReview.builder()
                .memberId(dto.getMemberId())
                .facilityMemberId(dto.getFacilityMemberId())
                .facilityId(dto.getFacilityId())
                .rating(dto.getRating())
                .content(dto.getContent())
                .imageUrl(dto.getImageUrl())
                .build();

        return toDto(repository.save(review));
    }

    @Override
    public FacilityReviewResponseDto get(Long id) {
        return repository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    @Override
    public List<FacilityReviewResponseDto> getByFacility(Long facilityId) {
        return repository.findByFacilityId(facilityId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // 🔥 변환 메서드 핵심
    private FacilityReviewResponseDto toDto(FacilityReview review) {
        return FacilityReviewResponseDto.builder()
                .id(review.getId())
                .memberId(review.getMemberId())
                .facilityId(review.getFacilityId())
                .rating(review.getRating())
                .content(review.getContent())
                .adminReply(review.getAdminReply())
                .imageUrl(review.getImageUrl())
                .isReported(review.getIsReported())
                .createdAt(review.getCreatedAt())
                .build();
    }
}