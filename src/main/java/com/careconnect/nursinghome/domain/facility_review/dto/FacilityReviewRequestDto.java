package com.careconnect.nursinghome.domain.facility_review.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FacilityReviewRequestDto {

    private Long memberId;
    private Long facilityMemberId;
    private Long facilityId;

    private Integer rating;
    private String content;
    private String imageUrl;
    private Object writtenAt;

}