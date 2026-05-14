package com.careconnect.nursinghome.domain.like.repository;

import com.careconnect.nursinghome.domain.like.entity.FacilityLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FacilityLikeRepository extends JpaRepository<FacilityLike, Long> {
    Optional<FacilityLike> findByMemberIdAndFacilityId(Long memberId, Long facilityId);
    List<FacilityLike> findByMemberId(Long memberId);
    boolean existsByMemberIdAndFacilityId(Long memberId, Long facilityId);

    // 👈 관리자 웹을 위해 추가: 특정 시설을 찜한 총 인원수
    long countByFacilityId(Long facilityId);
}