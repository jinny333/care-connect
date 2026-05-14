package com.careconnect.nursinghome.domain.facility.controller.admin;

import com.careconnect.nursinghome.domain.facility.dto.FacilityRequest;
import com.careconnect.nursinghome.domain.facility.service.FacilityService;
import com.careconnect.nursinghome.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/facilities") // 👈 /admin 추가
public class FacilityAdminController {

    private final FacilityService facilityService;

    // 새 요양원 등록
    @PostMapping
    public ApiResponse<Long> createFacility(@RequestBody FacilityRequest request) {
        return ApiResponse.ok(facilityService.createFacility(request));
    }

    // 요양원 정보 수정 (웹 대시보드에서 사용)
    @PutMapping("/{id}")
    public ApiResponse<Void> updateFacility(@PathVariable Long id,
                                            @RequestBody FacilityRequest request) {
        facilityService.updateFacility(id, request);
        return ApiResponse.ok(null);
    }

    // 요양원 삭제
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteFacility(@PathVariable Long id) {
        facilityService.deleteFacility(id);
        return ApiResponse.ok(null);
    }
}