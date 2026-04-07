package com.careconnect.nursinghome.domain.diagnosis.controller;

import com.careconnect.nursinghome.domain.diagnosis.entity.Diagnosis;
import com.careconnect.nursinghome.domain.diagnosis.service.DiagnosisService;
import com.careconnect.nursinghome.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/diagnosis")
public class DiagnosisController {

    private final DiagnosisService diagnosisService;

    @PostMapping("/results")
    public ApiResponse<Diagnosis> create(@RequestBody Diagnosis diagnosis) {
        return ApiResponse.ok(diagnosisService.create(diagnosis));
    }

    @GetMapping("/results/{id}")
    public ApiResponse<Diagnosis> get(@PathVariable Long id) {
        return ApiResponse.ok(diagnosisService.getById(id));
    }

    @GetMapping("/results/me")
    public ApiResponse<List<Diagnosis>> getByMember(@RequestParam Long memberId) {
        return ApiResponse.ok(diagnosisService.getByMember(memberId));
    }

    @PatchMapping("/results/{id}")
    public ApiResponse<Void> update(@PathVariable Long id, @RequestBody Diagnosis diagnosis) {
        diagnosisService.update(id, diagnosis);
        return ApiResponse.ok(null);
    }
}