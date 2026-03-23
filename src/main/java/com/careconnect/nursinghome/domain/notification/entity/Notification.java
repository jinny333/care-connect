package com.careconnect.nursinghome.domain.notification.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 알림 번호

    private Long memberId; // 회원 번호

    private String content; // 내용

    @Enumerated(EnumType.STRING)
    private NotificationType type; // 유형 (enum)

    private Boolean isRead; // 확인 여부

    private LocalDateTime createdAt; // 생성 일시

    // 🔥 자동 세팅
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.isRead = false;
    }
}