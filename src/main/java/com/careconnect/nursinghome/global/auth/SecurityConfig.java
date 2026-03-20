package com.careconnect.nursinghome.global.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 비밀번호를 암호화해주는 도구예요! (BCrypt 방식)
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // API 서버라 CSRF는 꺼둘게요
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // JWT 쓸 거라 세션 안 써요

                .authorizeHttpRequests(auth -> auth
                        // 1. 회원가입과 로그인은 '누구나' 접근 가능하게 열어줘!
                        .requestMatchers("/api/v1/members/signup", "/api/v1/auth/login").permitAll()
                        // 2. 나머지는 나중에 '인증'된 사람만 쓰게 할 거야 (지금은 테스트를 위해 일단 다 열어둘게요)
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}