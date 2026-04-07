package com.careconnect.nursinghome.domain.facility_review.controller;

import com.careconnect.nursinghome.domain.facility_review.dto.FacilityReviewRequestDto;
import com.careconnect.nursinghome.domain.facility_review.dto.FacilityReviewResponseDto;
import com.careconnect.nursinghome.domain.facility_review.service.FacilityReviewService;
import com.careconnect.nursinghome.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class FacilityReviewController {

    private final FacilityReviewService service;

    @PostMapping
    public ApiResponse<FacilityReviewResponseDto> create(@RequestBody FacilityReviewRequestDto dto) {
        return ApiResponse.ok(service.create(dto));
    }

    @GetMapping("/{id}")
    public ApiResponse<FacilityReviewResponseDto> get(@PathVariable Long id) {
        return ApiResponse.ok(service.get(id));
    }

    @GetMapping("/facility/{facilityId}")
    public ApiResponse<List<FacilityReviewResponseDto>> getByFacility(@PathVariable Long facilityId) {
        return ApiResponse.ok(service.getByFacility(facilityId));
    }

    @PatchMapping("/{id}")
    public ApiResponse<Void> update(@PathVariable Long id, @RequestBody FacilityReviewRequestDto dto) {
        service.update(id, dto);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok(null);
    }
}