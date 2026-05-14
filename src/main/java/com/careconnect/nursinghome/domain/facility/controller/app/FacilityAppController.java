package com.careconnect.nursinghome.domain.facility.controller.app;

import com.careconnect.nursinghome.domain.facility.dto.FacilityResponse;
import com.careconnect.nursinghome.domain.facility.service.FacilityService;
import com.careconnect.nursinghome.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/app/facilities") // 👈 /app 추가
public class FacilityAppController {

    private final FacilityService facilityService;

    // 모든 요양원 목록 조회
    @GetMapping
    public ApiResponse<List<FacilityResponse>> getFacilities() {
        return ApiResponse.ok(facilityService.getFacilities());
    }

    // 요양원 상세 조회
    @GetMapping("/{id}")
    public ApiResponse<FacilityResponse> getFacility(@PathVariable Long id) {
        return ApiResponse.ok(facilityService.getFacility(id));
    }

    // 키워드 검색
    @GetMapping("/search/keyword")
    public ApiResponse<List<FacilityResponse>> searchFacilities(@RequestParam String keyword) {
        return ApiResponse.ok(facilityService.searchFacilities(keyword));
    }

    // 위치 기반 검색
    @GetMapping("/search")
    public ApiResponse<List<FacilityResponse>> getFacilitiesByLocation(
            @RequestParam Double lat,
            @RequestParam Double lon,
            @RequestParam(defaultValue = "5") Double radius) {
        return ApiResponse.ok(facilityService.getFacilitiesByLocation(lat, lon, radius));
    }
}