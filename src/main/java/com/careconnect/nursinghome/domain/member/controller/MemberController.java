package com.careconnect.nursinghome.domain.member.controller;

import com.careconnect.nursinghome.domain.member.dto.MemberJoinRequest;
import com.careconnect.nursinghome.domain.member.dto.MemberJoinResponse;
import com.careconnect.nursinghome.domain.member.dto.MemberResponseDto;
import com.careconnect.nursinghome.domain.member.dto.MemberUpdateDto;
import com.careconnect.nursinghome.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

//    @GetMapping("/me")
//    public ResponseEntity<MemberResponseDto> findMemberInfoById() {
//        // 아직 로그인이 없으므로, 테스트를 위해 DB에 저장된 1번 사용자를 조회한다고 가정합니다.
//        // 나중에 Security(토큰)를 적용하면 이 부분이 "현재 로그인 유저 ID"로 바뀔 거예요!
//        Long tempId = 1L;
//        return ResponseEntity.ok(memberService.getMyInfo(tempId));
//    }
//
//    @PatchMapping("/me")
//    public ResponseEntity<Long> updateMemberInfo(@RequestBody MemberUpdateDto updateDto) {
//        // 임시 ID 사용 (나중에 토큰으로 교체!)
//        Long tempId = 1L;
//        return ResponseEntity.ok(memberService.updateMyInfo(tempId, updateDto));
//    }

    // 1. 내 정보 조회 (기존 GetMapping 수정)
    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> findMemberInfoById() {
        Long tempId = 3L; // 👈 여기도 3번으로 고정!
        return ResponseEntity.ok(memberService.getMyInfo(tempId));
    }

    // 2. 내 정보 수정 (기존 PatchMapping 두 개를 이거 하나로 합치세요!)
    @PatchMapping("/me")
    public ResponseEntity<Long> updateMyInfo(
            // @AuthenticationPrincipal 대신 테스트를 위해 ID를 직접 지정해봅시다!
            // 아까 가입했을 때 성공한 ID가 3번이었죠? 그걸로 테스트해볼게요.
            @RequestBody MemberUpdateDto updateDto) {

        Long tempId = 3L; // 👈 아까 가입 성공한 ID로 임시 고정!
        Long updatedId = memberService.updateMyInfo(tempId, updateDto);
        return ResponseEntity.ok(updatedId);
    }
}