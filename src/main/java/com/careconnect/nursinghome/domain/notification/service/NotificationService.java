package com.careconnect.nursinghome.domain.notification.service;

import com.careconnect.nursinghome.domain.notification.entity.Notification;

import java.util.List;

public interface NotificationService {

    Notification create(Notification notification);

    List<Notification> getByMember(Long memberId);

    List<Notification> getUnread(Long memberId);

    void read(Long id);
}