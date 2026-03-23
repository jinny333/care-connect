package com.careconnect.nursinghome.domain.facility.controller;

import com.careconnect.nursinghome.domain.facility.dto.FacilityRequest;
import com.careconnect.nursinghome.domain.facility.dto.FacilityResponse;
import com.careconnect.nursinghome.domain.facility.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/facilities")
public class FacilityController {

    private final FacilityService facilityService;

    // 1. 시설 등록
    @PostMapping
    public Long createFacility(@RequestBody FacilityRequest request) {
        return facilityService.createFacility(request);
    }

    // 2. 시설 전체 조회
    @GetMapping
    public List<FacilityResponse> getFacilities() {
        return facilityService.getFacilities();
    }

    // 3. 시설 단건 조회
    @GetMapping("/{id}")
    public FacilityResponse getFacility(@PathVariable Long id) {
        return facilityService.getFacility(id);
    }

    // 4. 시설 수정
    @PutMapping("/{id}")
    public void updateFacility(@PathVariable Long id,
                               @RequestBody FacilityRequest request) {
        facilityService.updateFacility(id, request);
    }

    // 5. 시설 삭제
    @DeleteMapping("/{id}")
    public void deleteFacility(@PathVariable Long id) {
        facilityService.deleteFacility(id);
    }
}