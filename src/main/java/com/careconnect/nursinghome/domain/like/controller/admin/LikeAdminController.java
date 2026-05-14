package com.careconnect.nursinghome.domain.like.controller.admin;

import com.careconnect.nursinghome.domain.like.service.FacilityLikeService;
import com.careconnect.nursinghome.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/likes") // 👈 /admin 추가
public class LikeAdminController {

    private final FacilityLikeService facilityLikeService;

    // 우리 요양원을 즐겨찾기한 총 인원수 조회
    @GetMapping("/facility/{facilityId}/count")
    public ApiResponse<Long> getLikeCount(@PathVariable Long facilityId) {
        return ApiResponse.ok(facilityLikeService.getLikeCount(facilityId));
    }
}