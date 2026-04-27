package com.careconnect.nursinghome.domain.like.controller;

import com.careconnect.nursinghome.domain.facility.dto.FacilityResponse;
import com.careconnect.nursinghome.domain.like.service.FacilityLikeService;
import com.careconnect.nursinghome.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/likes")
public class FacilityLikeController {

    private final FacilityLikeService facilityLikeService;

    @PostMapping("/{facilityId}")
    public ApiResponse<Boolean> toggleLike(
            @PathVariable Long facilityId,
            Principal principal) {
        Long memberId = Long.parseLong(principal.getName());
        return ApiResponse.ok(facilityLikeService.toggleLike(memberId, facilityId));
    }

    @GetMapping
    public ApiResponse<List<FacilityResponse>> getLikedFacilities(Principal principal) {
        Long memberId = Long.parseLong(principal.getName());
        return ApiResponse.ok(facilityLikeService.getLikedFacilities(memberId));
    }

    @GetMapping("/{facilityId}")
    public ApiResponse<Boolean> isLiked(
            @PathVariable Long facilityId,
            Principal principal) {
        Long memberId = Long.parseLong(principal.getName());
        return ApiResponse.ok(facilityLikeService.isLiked(memberId, facilityId));
    }
}