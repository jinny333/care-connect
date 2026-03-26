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
    private Role role;
    private LocalDate birthDate;

    public static MemberResponseDto from(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole())
                .build();
    }
}