package com.careconnect.nursinghome.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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

    private String provider;   // 예: "naver", "kakao"
    private String providerId; // 네이버에서 제공하는 고유 식별자 (sub 혹은 id)

    private Boolean isDeleted; // 탈퇴 여부
    private Long institutionId; // 기관 번호
    private LocalDate birthDate; // 생년월일

    // 생성일시와 수정일시는 보통 LocalDateTime을 써!
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Column(length = 500)
    private String refreshToken;

    @Builder
    public Member(Role role, String name, String email, String password, String phoneNumber,
                  String provider, String providerId, Boolean isDeleted, Long institutionId,
                  LocalDate birthDate) {
        this.role = role;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.provider = provider;       // "naver" 등이 들어올 자리
        this.providerId = providerId;   // 네이버 고유 식별자 값
        this.isDeleted = isDeleted != null ? isDeleted : false; // 기본값 처리
        this.institutionId = institutionId;
        this.birthDate = birthDate;

        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = MemberStatus.ACTIVE; // 초기 상태는 항상 ACTIVE
    }

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MemberStatus status = MemberStatus.ACTIVE;

    private LocalDateTime withdrawnAt;

    public enum MemberStatus {
        ACTIVE, WITHDRAWN
    }

    public void update(String name, String phoneNumber) {
        if (name != null) this.name = name;
        if (phoneNumber != null) this.phoneNumber = phoneNumber;
        this.updatedAt = LocalDateTime.now(); // 수정 시간 업데이트
    }


    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    // 소셜 정보를 업데이트하거나 연결하는 메서드
    public void linkSocial(String provider, String providerId) {
        this.provider = provider;
        this.providerId = providerId;
    }
}