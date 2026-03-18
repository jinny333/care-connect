package com.careconnect.nursinghome.domain.member.controller;

import com.careconnect.nursinghome.domain.member.dto.MemberJoinRequest;
import com.careconnect.nursinghome.domain.member.dto.MemberJoinResponse;
import com.careconnect.nursinghome.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members") // 명세서의 기본 Endpoint 주소
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입 API
     * 명세서 주소: POST /api/v1/members/signup
     */
    @PostMapping("/signup")
    public ResponseEntity<MemberJoinResponse> signup(@RequestBody MemberJoinRequest request) {
        // 서비스 호출해서 회원가입 진행
        MemberJoinResponse response = memberService.join(request);

        // 성공 시 200 OK와 함께 생성된 회원 정보 응답
        return ResponseEntity.ok(response);
    }
}