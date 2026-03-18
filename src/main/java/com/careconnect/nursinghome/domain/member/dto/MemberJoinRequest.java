package com.careconnect.nursinghome.domain.member.dto;

import com.careconnect.nursinghome.domain.member.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class MemberJoinRequest {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private Role role;
    private LocalDate birthDate;

    public Role getRole() {
        return this.role;
    }
}