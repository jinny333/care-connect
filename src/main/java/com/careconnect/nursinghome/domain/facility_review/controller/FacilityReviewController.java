package com.careconnect.nursinghome.domain.facility_review.controller;

import com.careconnect.nursinghome.domain.facility_review.dto.*;
import com.careconnect.nursinghome.domain.facility_review.service.FacilityReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class FacilityReviewController {

    private final FacilityReviewService service;

    @PostMapping
    public FacilityReviewResponseDto create(@RequestBody FacilityReviewRequestDto dto) {
        return service.create(dto);
    }

    @GetMapping("/{id}")
    public FacilityReviewResponseDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/facility/{facilityId}")
    public List<FacilityReviewResponseDto> getByFacility(@PathVariable Long facilityId) {
        return service.getByFacility(facilityId);
    }
}
