package com.careconnect.nursinghome.domain.facility_review.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class FacilityReviewResponseDto {

    private Long id;
    private Long memberId;
    private Long facilityId;

    private Integer rating;
    private String content;
    private String adminReply;
    private String imageUrl;

    private Boolean isReported;

    private LocalDateTime writtenAt;
    private LocalDateTime createdAt;
}
