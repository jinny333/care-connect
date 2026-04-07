package com.careconnect.nursinghome.domain.member.dto;

import com.careconnect.nursinghome.domain.member.entity.Member;
import com.careconnect.nursinghome.domain.member.entity.Role;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private String provider;
    private Role role;
    private LocalDate birthDate;

    // MemberResponseDto.java 안의 from 메서드
    public static MemberResponseDto from(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .provider(member.getProvider()) // ⭐ 이게 member.getProvider()로 잘 연결되어 있나요?
                .role(member.getRole() != null ? member.getRole() : null) // null 체크!
                .birthDate(member.getBirthDate())
                .build();
    }
}