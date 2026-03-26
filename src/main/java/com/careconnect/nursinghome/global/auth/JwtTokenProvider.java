package com.careconnect.nursinghome.global.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long accessTokenValidity;

    // 1. 유효기간 변수 추가 (보통 7일~14일)
    @Value("${jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenValidity;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
                            @Value("${jwt.access-token-validity-in-seconds}") long accessTokenValidity) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenValidity = accessTokenValidity * 1000;
    }

    // 토큰 생성
    public String createToken(Long memberId, String role) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + accessTokenValidity);

        return Jwts.builder()
                .setSubject(memberId.toString())
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰에서 회원 ID 추출
    public Long getMemberId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            // 잘못된 서명 등
        } catch (ExpiredJwtException e) {
            // 만료된 토큰
        } catch (UnsupportedJwtException e) {
            // 지원되지 않는 토큰
        } catch (IllegalArgumentException e) {
            // 잘못된 토큰
        }
        return false;
    }

    // 토큰의 남은 유효 시간(ms) 추출
    public long getExpiration(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        // 현재 시간과 만료 시간의 차이를 계산
        long now = new Date().getTime();
        return (expiration.getTime() - now);
    }

    // 2. Refresh Token 생성 메서드
    public String createRefreshToken(Long memberId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshTokenValidity * 1000);

        return Jwts.builder()
                .setSubject(memberId.toString()) // 누구 건지 기록
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public org.springframework.security.core.Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        // 1. 토큰에 저장된 role 꺼내기 (지나 님이 createToken할 때 넣었던 그 "role"!)
        String role = claims.get("role").toString();

        // 2. 스프링 시큐리티가 이해할 수 있게 ROLE_ADMIN 형태로 변환
        java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> authorities =
                java.util.Arrays.stream(role.split(","))
                        .map(r -> new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_" + r))
                        .collect(java.util.stream.Collectors.toList());

        // 3. 유저 정보를 담은 객체 생성 (비밀번호는 보안상 빈 값 "")
        org.springframework.security.core.userdetails.User principal =
                new org.springframework.security.core.userdetails.User(claims.getSubject(), "", authorities);

        // 4. 최종 인증 객체 반환
        return new org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken(principal, token, authorities);
    }
}