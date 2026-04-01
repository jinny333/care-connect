package com.careconnect.nursinghome.domain.diagnosis.controller;

import com.careconnect.nursinghome.domain.diagnosis.entity.Diagnosis;
import com.careconnect.nursinghome.domain.diagnosis.service.DiagnosisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diagnosis")
public class DiagnosisController {

    private final DiagnosisService diagnosisService;

    @PostMapping
    public Diagnosis create(@RequestBody Diagnosis diagnosis) {
        return diagnosisService.create(diagnosis);
    }

    @GetMapping("/{id}")
    public Diagnosis get(@PathVariable Long id) {
        return diagnosisService.getById(id);
    }

    // 추가
    @GetMapping("/member/{memberId}")
    public List<Diagnosis> getByMember(@PathVariable Long memberId) {
        return diagnosisService.getByMember(memberId);
    }

    // 추가
    @PatchMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody Diagnosis diagnosis) {
        diagnosisService.update(id, diagnosis);
    }
}