package com.careconnect.nursinghome.domain.notification.service;

import com.careconnect.nursinghome.domain.notification.entity.Notification;
import com.careconnect.nursinghome.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;

    @Override
    public Notification create(Notification notification) {
        return repository.save(notification);
    }

    @Override
    public List<Notification> getByMember(Long memberId) {
        return repository.findByMemberId(memberId);
    }

    @Override
    public List<Notification> getUnread(Long memberId) {
        return repository.findByMemberIdAndIsReadFalse(memberId);
    }

    @Override
    public void read(Long id) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notification.setIsRead(true);
        repository.save(notification);
    }
}