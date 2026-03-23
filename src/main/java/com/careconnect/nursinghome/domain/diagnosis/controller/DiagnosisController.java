package com.careconnect.nursinghome.domain.diagnosis.controller;

import com.careconnect.nursinghome.domain.diagnosis.entity.Diagnosis;
import com.careconnect.nursinghome.domain.diagnosis.service.DiagnosisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diagnosis")
public class DiagnosisController {

    private final DiagnosisService diagnosisService;

    // 🔥 진단 + 상세 같이 생성
    @PostMapping
    public Diagnosis create(@RequestBody Diagnosis diagnosis) {
        return diagnosisService.create(diagnosis);
    }

    // 조회
    @GetMapping("/{id}")
    public Diagnosis get(@PathVariable Long id) {
        return diagnosisService.getById(id);
    }
}