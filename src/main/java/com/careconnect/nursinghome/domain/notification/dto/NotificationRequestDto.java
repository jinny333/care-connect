package com.careconnect.nursinghome.domain.notification.dto;

import com.careconnect.nursinghome.domain.notification.entity.NotificationType;
import lombok.Getter;

@Getter
public class NotificationRequestDto {
    private Long memberId;
    private String content;
    private NotificationType type;
}