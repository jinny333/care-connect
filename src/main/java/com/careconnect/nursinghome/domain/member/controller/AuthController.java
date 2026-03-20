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
}