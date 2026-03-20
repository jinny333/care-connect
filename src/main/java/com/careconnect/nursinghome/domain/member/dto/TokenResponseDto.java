package com.careconnect.nursinghome.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TokenResponseDto {
    private String accessToken;
    private String refreshToken; // 나중에 구현할 거지만 미리 넣어둘게요!
}