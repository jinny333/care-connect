package com.careconnect.nursinghome.domain.member.controller;

import com.careconnect.nursinghome.domain.member.dto.LoginRequestDto;
import com.careconnect.nursinghome.domain.member.dto.TokenResponseDto;
import com.careconnect.nursinghome.domain.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        // AuthService의 login 메서드를 호출해서 토큰을 받아옵니다.
        TokenResponseDto tokenResponse = authService.login(loginRequest);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponseDto> reissue(@RequestBody String refreshToken) {
        // AuthService에 아까 만든 reissue 로직을 호출합니다!
        TokenResponseDto tokenResponse = authService.reissue(refreshToken);
        return ResponseEntity.ok(tokenResponse);
    }
}