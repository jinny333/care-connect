package com.careconnect.nursinghome.domain.facility_review.controller.admin;

import com.careconnect.nursinghome.domain.facility_review.dto.FacilityReviewResponseDto;
import com.careconnect.nursinghome.domain.facility_review.service.FacilityReviewService;
import com.careconnect.nursinghome.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/reviews") // 👈 /admin 추가
public class FacilityReviewAdminController {

    private final FacilityReviewService service;

    // 우리 요양원에 달린 전체 리뷰 보기
    @GetMapping("/facility/{facilityId}")
    public ApiResponse<List<FacilityReviewResponseDto>> getByFacility(@PathVariable Long facilityId) {
        return ApiResponse.ok(service.getByFacility(facilityId));
    }

    // 리뷰에 원장님 답글 달기/수정하기
    @PatchMapping("/{id}/reply")
    public ApiResponse<Void> updateReply(@PathVariable Long id, @RequestBody String reply) {
        service.updateAdminReply(id, reply);
        return ApiResponse.ok(null);
    }

    // 부적절한 리뷰 삭제 (관리자 권한)
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok(null);
    }
}