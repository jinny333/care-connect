package com.careconnect.nursinghome.domain.member.controller;

import com.careconnect.nursinghome.domain.member.dto.MemberJoinRequest;
import com.careconnect.nursinghome.domain.member.dto.MemberJoinResponse;
import com.careconnect.nursinghome.domain.member.dto.MemberResponseDto;
import com.careconnect.nursinghome.domain.member.dto.MemberUpdateDto;
import com.careconnect.nursinghome.domain.member.service.MemberService;
import com.careconnect.nursinghome.global.auth.JwtTokenProvider; // 👈 추가 확인!
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final JwtTokenProvider tokenProvider; // 👈 1. 토큰 해석을 위해 추가!

    /**
     * 회원가입 API
     */
    @PostMapping("/signup")
    public ResponseEntity<MemberJoinResponse> signup(@RequestBody MemberJoinRequest request) {
        MemberJoinResponse response = memberService.join(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 1. 내 정보 조회 (JWT 적용)
     * 주소창에 ?token=... 이 있거나, 헤더에 토큰이 있는 경우 둘 다 처리
     */
    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> findMemberInfoById(
            @RequestParam(value = "token", required = false) String token, // 👈 주소창 토큰 읽기
            @AuthenticationPrincipal User user) { // 👈 시큐리티 인증 정보 읽기

        Long memberId;

        if (token != null && !token.isEmpty()) {
            // 주소창에 토큰이 있는 경우 (나희님 테스트용)
            memberId = tokenProvider.getMemberId(token);
        } else if (user != null) {
            // 이미 필터를 거쳐 인증된 경우
            memberId = Long.parseLong(user.getUsername());
        } else {
            throw new RuntimeException("로그인이 필요합니다.");
        }

        return ResponseEntity.ok(memberService.getMyInfo(memberId));
    }

    /**
     * 2. 내 정보 수정 (임시 ID 3L 삭제!)
     */
    @PatchMapping("/me")
    public ResponseEntity<Long> updateMyInfo(
            @AuthenticationPrincipal User user, // 👈 진짜 유저 정보 받기
            @RequestBody MemberUpdateDto updateDto) {

        if (user == null) throw new RuntimeException("로그인이 필요합니다.");

        Long memberId = Long.parseLong(user.getUsername());
        Long updatedId = memberService.updateMyInfo(memberId, updateDto);
        return ResponseEntity.ok(updatedId);
    }

    /**
     * 3. 회원 탈퇴
     */
    @DeleteMapping("/me")
    public ResponseEntity<String> withdraw(@AuthenticationPrincipal User user) {
        if (user == null) throw new RuntimeException("로그인이 필요합니다.");

        // 이메일 대신 ID로 탈퇴하게 로직을 맞추는 게 좋지만,
        // 일단 기존 서비스 로직(이메일 사용)에 맞춰 user에서 이메일을 추출할 수도 있습니다.
        // 현재 user.getUsername()에 ID가 들어있으므로, DB에서 다시 조회해서 처리하거나 서비스를 수정해야 해요.
        // 일단은 지나님이 쓰시던 방식 유지 혹은 ID 기반으로 수정 권장!
        memberService.withdrawMemberById(Long.parseLong(user.getUsername()));
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String bearerToken) {
        String accessToken = bearerToken.substring(7);
        memberService.logout(accessToken);
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
}