package com.careconnect.nursinghome.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 회원 번호

    @Enumerated(EnumType.STRING) // Enum을 문자열로 DB에 저장
    private Role role; // 역할 (CUSTOMER, INSTITUTION, ADMIN)

    private String name; // 성명
    private String email; // 이메일
    private String password; // 비밀번호
    private String phoneNumber; // 전화번호
    private String naverLink; // 네이버 연동 (String)
    private Boolean isDeleted; // 탈퇴 여부
    private Long institutionId; // 기관 번호
    private LocalDate birthDate; // 생년월일

    // 생성일시와 수정일시는 보통 LocalDateTime을 써!
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Member(Role role, String name, String email, String password, String phoneNumber,
                  String naverLink, Boolean isDeleted, Long institutionId,
                  LocalDate birthDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.role = role;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.naverLink = naverLink;
        this.isDeleted = isDeleted;
        this.institutionId = institutionId;
        this.birthDate = birthDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void update(String name, String phoneNumber) {
        if (name != null) this.name = name;
        if (phoneNumber != null) this.phoneNumber = phoneNumber;
        this.updatedAt = LocalDateTime.now(); // 수정 시간 업데이트
    }
}