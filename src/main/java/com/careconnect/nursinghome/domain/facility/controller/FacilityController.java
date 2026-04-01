package com.careconnect.nursinghome.domain.facility.controller;

import com.careconnect.nursinghome.domain.facility.dto.FacilityRequest;
import com.careconnect.nursinghome.domain.facility.dto.FacilityResponse;
import com.careconnect.nursinghome.domain.facility.service.FacilityService;
import com.careconnect.nursinghome.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/facilities")
public class FacilityController {

    private final FacilityService facilityService;

    @PostMapping
    public ApiResponse<Long> createFacility(@RequestBody FacilityRequest request) {
        return ApiResponse.ok(facilityService.createFacility(request));
    }

    @GetMapping
    public ApiResponse<List<FacilityResponse>> getFacilities() {
        return ApiResponse.ok(facilityService.getFacilities());
    }

    @GetMapping("/{id}")
    public ApiResponse<FacilityResponse> getFacility(@PathVariable Long id) {
        return ApiResponse.ok(facilityService.getFacility(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> updateFacility(@PathVariable Long id,
                                            @RequestBody FacilityRequest request) {
        facilityService.updateFacility(id, request);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteFacility(@PathVariable Long id) {
        facilityService.deleteFacility(id);
        return ApiResponse.ok(null);
    }

}