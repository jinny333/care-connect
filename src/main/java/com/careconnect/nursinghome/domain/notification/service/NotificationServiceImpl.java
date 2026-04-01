package com.careconnect.nursinghome.domain.notification.service;

import com.careconnect.nursinghome.domain.notification.dto.NotificationRequestDto;
import com.careconnect.nursinghome.domain.notification.dto.NotificationResponseDto;
import com.careconnect.nursinghome.domain.notification.entity.Notification;
import com.careconnect.nursinghome.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;

    @Override
    public NotificationResponseDto create(NotificationRequestDto dto) {
        Notification notification = Notification.builder()
                .memberId(dto.getMemberId())
                .content(dto.getContent())
                .type(dto.getType())
                .build();
        return toDto(repository.save(notification));
    }

    @Override
    public List<NotificationResponseDto> getByMember(Long memberId) {
        return repository.findByMemberId(memberId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationResponseDto> getUnread(Long memberId) {
        return repository.findByMemberIdAndIsReadFalse(memberId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void read(Long id) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setIsRead(true);
        repository.save(notification);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private NotificationResponseDto toDto(Notification n) {
        return NotificationResponseDto.builder()
                .id(n.getId())
                .memberId(n.getMemberId())
                .content(n.getContent())
                .type(n.getType())
                .isRead(n.getIsRead())
                .createdAt(n.getCreatedAt())
                .build();
    }
}