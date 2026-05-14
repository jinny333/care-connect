package com.careconnect.nursinghome.domain.facility_review.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilityReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;          // 고객
    private Long facilityMemberId;  // 기관 회원
    private Long facilityId;        // 기관 번호

    private Integer rating;         // 별점
    private String content;         // 리뷰 내용
    private String adminReply;      // 관리자 답변
    private String imageUrl;        // 사진

    private Boolean isReported;     // 부적절 여부

    private LocalDateTime writtenAt;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.writtenAt = LocalDateTime.now();
        this.isReported = false;
    }

    public void update(Integer rating, String content, String imageUrl) {
        this.rating = rating;
        this.content = content;
        this.imageUrl = imageUrl;
        this.writtenAt = LocalDateTime.now();
    }

    public void updateAdminReply(String adminReply) {
        this.adminReply = adminReply;
    }
}