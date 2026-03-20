package com.careconnect.nursinghome.domain.member.dto;

import com.careconnect.nursinghome.domain.member.entity.Role;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private String email;
    private String name;
    private String phoneNumber;
    private Role role;
    private LocalDate birthDate;
}