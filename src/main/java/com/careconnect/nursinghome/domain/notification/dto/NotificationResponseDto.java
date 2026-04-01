package com.careconnect.nursinghome.domain.notification.dto;

import com.careconnect.nursinghome.domain.notification.entity.NotificationType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NotificationResponseDto {
    private Long id;
    private Long memberId;
    private String content;
    private NotificationType type;
    private Boolean isRead;
    private LocalDateTime createdAt;
}