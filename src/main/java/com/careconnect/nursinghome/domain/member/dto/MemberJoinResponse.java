package com.careconnect.nursinghome.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberJoinResponse {
    private Long id;
    private String email;
    private String name;
}