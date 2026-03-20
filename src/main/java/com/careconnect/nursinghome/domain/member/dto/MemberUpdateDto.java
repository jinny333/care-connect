package com.careconnect.nursinghome.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateDto {
    private String name;
    private String phoneNumber;
}