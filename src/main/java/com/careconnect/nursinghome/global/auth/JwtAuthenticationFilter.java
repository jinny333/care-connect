package com.careconnect.nursinghome.global.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate; // Redis 주입!

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. 헤더에서 토큰 추출
        String token = resolveToken(request);

        // 2. 토큰 유효성 검사
        if (token != null && jwtTokenProvider.validateToken(token)) {
            org.springframework.security.core.Authentication authentication = jwtTokenProvider.getAuthentication(token);
            org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authentication);
            // ⭐ [실무 핵심] Redis에 해당 토큰이 로그아웃된 상태로 저장되어 있는지 확인
//            String isLogout = redisTemplate.opsForValue().get(token);
//
//            if (ObjectUtils.isEmpty(isLogout)) {
//                // 블랙리스트에 없다면? -> 정상적인 사용자로 인증 처리 (나중에 구현!)
//                // 지금은 로직 흐름만 잡아둘게요.
//            } else {
//                // 블랙리스트에 있다면? -> 로그아웃된 토큰이므로 차단!
//                System.out.println("로그아웃된 토큰으로 접근 시도 차단!");
//            }
            System.out.println("토큰 검증 및 인증 객체 등록 성공: " + token);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}