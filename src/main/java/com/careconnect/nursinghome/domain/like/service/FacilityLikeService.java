package com.careconnect.nursinghome.domain.like.service;

import com.careconnect.nursinghome.domain.facility.dto.FacilityResponse;
import com.careconnect.nursinghome.domain.facility.repository.FacilityRepository;
import com.careconnect.nursinghome.domain.like.entity.FacilityLike;
import com.careconnect.nursinghome.domain.like.repository.FacilityLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacilityLikeService {

    private final FacilityLikeRepository facilityLikeRepository;
    private final FacilityRepository facilityRepository;

    public boolean toggleLike(Long memberId, Long facilityId) {
        return facilityLikeRepository.findByMemberIdAndFacilityId(memberId, facilityId)
                .map(like -> {
                    facilityLikeRepository.delete(like);
                    return false;
                })
                .orElseGet(() -> {
                    facilityLikeRepository.save(FacilityLike.builder()
                            .memberId(memberId)
                            .facilityId(facilityId)
                            .build());
                    return true;
                });
    }

    public List<FacilityResponse> getLikedFacilities(Long memberId) {
        return facilityLikeRepository.findByMemberId(memberId)
                .stream()
                .map(like -> facilityRepository.findById(like.getFacilityId())
                        .map(FacilityResponse::new)
                        .orElse(null))
                .filter(f -> f != null)
                .collect(Collectors.toList());
    }

    public boolean isLiked(Long memberId, Long facilityId) {
        return facilityLikeRepository.existsByMemberIdAndFacilityId(memberId, facilityId);
    }
}