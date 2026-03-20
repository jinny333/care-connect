package com.careconnect.nursinghome.domain.member.service;

import com.careconnect.nursinghome.domain.member.dto.LoginRequestDto;
import com.careconnect.nursinghome.domain.member.dto.TokenResponseDto;
import com.careconnect.nursinghome.domain.member.entity.Member;
import com.careconnect.nursinghome.domain.member.repository.MemberRepository;
import com.careconnect.nursinghome.global.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenResponseDto login(LoginRequestDto loginRequest) {
        // 1. 이메일로 회원 찾기
        Member member = memberRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("가입되지 않은 이메일입니다."));

        // 2. 비밀번호 일치 확인 (지금은 생텍스트로 비교하고, 나중에 암호화 적용할게요!)
        if (!passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 토큰 생성 및 반환
        String accessToken = jwtTokenProvider.createToken(member.getId(), member.getRole().name());

        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .build();
    }
}