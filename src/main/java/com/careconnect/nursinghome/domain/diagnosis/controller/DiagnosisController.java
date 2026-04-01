package com.careconnect.nursinghome.domain.diagnosis.controller;

import com.careconnect.nursinghome.domain.diagnosis.entity.Diagnosis;
import com.careconnect.nursinghome.domain.diagnosis.service.DiagnosisService;
import com.careconnect.nursinghome.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diagnosis")
public class DiagnosisController {

    private final DiagnosisService diagnosisService;

    @PostMapping
    public ApiResponse<Diagnosis> create(@RequestBody Diagnosis diagnosis) {
        return ApiResponse.ok(diagnosisService.create(diagnosis));
    }

    @GetMapping("/{id}")
    public ApiResponse<Diagnosis> get(@PathVariable Long id) {
        return ApiResponse.ok(diagnosisService.getById(id));
    }

    @GetMapping("/member/{memberId}")
    public ApiResponse<List<Diagnosis>> getByMember(@PathVariable Long memberId) {
        return ApiResponse.ok(diagnosisService.getByMember(memberId));
    }

    @PatchMapping("/{id}")
    public ApiResponse<Void> update(@PathVariable Long id, @RequestBody Diagnosis diagnosis) {
        diagnosisService.update(id, diagnosis);
        return ApiResponse.ok(null);
    }
}