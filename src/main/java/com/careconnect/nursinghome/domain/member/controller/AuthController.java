package com.careconnect.nursinghome.domain.member.controller;

import com.careconnect.nursinghome.domain.member.dto.LoginRequestDto;
import com.careconnect.nursinghome.domain.member.dto.TokenResponseDto;
import com.careconnect.nursinghome.domain.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j; // 1. 로그를 위해 추가
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j // 2. 로그를 위해 추가
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        // [로그] 나희님이 로그인 시도할 때 이메일 찍어보기
        log.info("🔑 [연동 테스트] 로그인 요청 발생! - 이메일: {}", loginRequest.getEmail());

        try {
            TokenResponseDto tokenResponse = authService.login(loginRequest);
            log.info("[연동 성공] {}님 로그인 성공! 토큰을 반환합니다.", loginRequest.getEmail());
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            log.error("[연동 실패] 로그인 중 에러 발생: {}", e.getMessage());
            throw e;
        }
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponseDto> reissue(@RequestBody String refreshToken) {
        log.info("[연동 테스트] 토큰 재발급 요청 들어옴");
        TokenResponseDto tokenResponse = authService.reissue(refreshToken);
        return ResponseEntity.ok(tokenResponse);
    }
}