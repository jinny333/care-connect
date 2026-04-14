package com.careconnect.nursinghome.global.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    // 생성자 방지 (유틸성 클래스니까)
    private SecurityUtil() {}

    /**
     * 현재 SecurityContext에 저장된 사용자의 memberId를 반환해.
     * JwtAuthenticationFilter에서 저장해준 정보를 꺼내 쓰는 거야!
     */
    public static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null || authentication.getName().equals("anonymousUser")) {
            // 이 에러가 뜨면 로그인이 안 된 상태에서 기능을 쓰려고 한 거야
            throw new RuntimeException("Security Context에 인증 정보가 없습니다.");
        }

        // 우리가 JWT Subject에 memberId를 넣어놨으니까 getName()으로 꺼낼 수 있어
        return Long.parseLong(authentication.getName());
    }
}