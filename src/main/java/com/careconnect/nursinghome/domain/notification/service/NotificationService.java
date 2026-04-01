package com.careconnect.nursinghome.domain.notification.service;

import com.careconnect.nursinghome.domain.notification.dto.NotificationRequestDto;
import com.careconnect.nursinghome.domain.notification.dto.NotificationResponseDto;

import java.util.List;

public interface NotificationService {
    NotificationResponseDto create(NotificationRequestDto dto);
    List<NotificationResponseDto> getByMember(Long memberId);
    List<NotificationResponseDto> getUnread(Long memberId);
    void read(Long id);
    void delete(Long id);  // 추가
}