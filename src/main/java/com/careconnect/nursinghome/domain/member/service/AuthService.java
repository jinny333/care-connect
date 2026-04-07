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
                .filter(m -> m.getStatus() == Member.MemberStatus.ACTIVE)
                .orElseThrow(() -> new RuntimeException("가입되지 않은 이메일입니다."));

        // 2. 비밀번호 일치 확인 (지금은 생텍스트로 비교하고, 나중에 암호화 적용할게요!)
        if (!passwordEncoder.matches(loginRequest.getPassword(), member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 토큰 생성 및 반환
        String accessToken = jwtTokenProvider.createToken(member.getId(), member.getRole().name());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        member.updateRefreshToken(refreshToken);

        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // AuthService.java

    @Transactional
    public TokenResponseDto reissue(String refreshToken) {
        // 1~3단계: 기존 검증 로직 (Refresh Token 유효성 및 DB 대조)
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("연장권이 만료되었습니다. 다시 로그인하세요.");
        }

        Long memberId = jwtTokenProvider.getMemberId(refreshToken);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

        if (!refreshToken.equals(member.getRefreshToken())) {
            throw new RuntimeException("토큰 정보가 일치하지 않습니다. 부정 접근이 의심됩니다!");
        }

        // Access Token 뿐만 아니라 Refresh Token도 새로 뽑습니다!
        String newAccessToken = jwtTokenProvider.createToken(member.getId(), member.getRole().name());
        String newRefreshToken = jwtTokenProvider.createRefreshToken(member.getId());

        // DB에 새 연장권을 저장해서 '헌 토큰'은 못 쓰게 만듭니다.
        member.updateRefreshToken(newRefreshToken);

        return TokenResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken) // 새 연장권을 클라이언트에게 전달!
                .build();
    }
}