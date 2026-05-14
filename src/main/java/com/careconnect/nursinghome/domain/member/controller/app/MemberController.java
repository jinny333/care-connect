package com.careconnect.nursinghome.domain.member.controller.app;

import com.careconnect.nursinghome.domain.member.dto.MemberJoinRequest;
import com.careconnect.nursinghome.domain.member.dto.MemberJoinResponse;
import com.careconnect.nursinghome.domain.member.dto.MemberResponseDto;
import com.careconnect.nursinghome.domain.member.dto.MemberUpdateDto;
import com.careconnect.nursinghome.domain.member.service.MemberService;
import com.careconnect.nursinghome.global.auth.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입 API
     */
    @PostMapping("/signup")
    public ResponseEntity<MemberJoinResponse> signup(@RequestBody MemberJoinRequest request) {
        // getLoginId() 대신 getEmail()을 사용!
        log.info("📝 [연동 테스트] 회원가입 요청 발생! - 이메일: {}, 이름: {}", request.getEmail(), request.getName());

        MemberJoinResponse response = memberService.join(request);

        log.info("✅ [연동 성공] 회원가입 완료! - 등록된 ID: {}", response.getId());
        return ResponseEntity.ok(response);
    }

    /**
     * 1. 내 정보 조회 (JWT 토큰 기반)
     */
    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> findMemberInfoById() {
        Long memberId = SecurityUtil.getCurrentMemberId();
        log.info("[연동 테스트] 내 정보 조회 요청 - 토큰 추출 ID: {}", memberId);

        MemberResponseDto response = memberService.getMyInfo(memberId);

        log.info("[조회 완료] {}님 정보 반환 성공", response.getName());
        return ResponseEntity.ok(response);
    }

    /**
     * 2. 내 정보 수정 (JWT 토큰 기반)
     */
    @PatchMapping("/me")
    public ResponseEntity<Long> updateMyInfo(@RequestBody MemberUpdateDto updateDto) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        log.info("[연동 테스트] 정보 수정 시도 - 대상 ID: {}", memberId);

        Long updatedId = memberService.updateMyInfo(memberId, updateDto);

        log.info("[수정 완료] 회원 ID {}번 정보 업데이트 성공", updatedId);
        return ResponseEntity.ok(updatedId);
    }

    /**
     * 3. 회원 탈퇴 (JWT 토큰 기반)
     */
    @DeleteMapping("/me")
    public ResponseEntity<String> withdraw() {
        Long memberId = SecurityUtil.getCurrentMemberId();
        log.warn("[연동 테스트] 회원 탈퇴 요청! - 탈퇴 대상 ID: {}", memberId);

        memberService.withdrawMemberById(memberId);

        log.info("[탈퇴 완료] ID {}번 유저가 시스템을 떠났습니다.", memberId);
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }

    /**
     * 4. 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String bearerToken) {
        log.info("[연동 테스트] 로그아웃 요청 들어옴");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String accessToken = bearerToken.substring(7);
            memberService.logout(accessToken);

            log.info("[로그아웃 완료] 토큰 무효화 처리 성공");
            return ResponseEntity.ok("로그아웃 되었습니다.");
        }

        log.error("[로그아웃 실패] 토큰 형식이 잘못되었습니다: {}", bearerToken);
        return ResponseEntity.badRequest().body("잘못된 토큰 형식입니다.");
    }
}