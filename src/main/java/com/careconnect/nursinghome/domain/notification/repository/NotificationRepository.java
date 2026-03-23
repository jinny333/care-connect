package com.careconnect.nursinghome.domain.notification.repository;

import com.careconnect.nursinghome.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByMemberId(Long memberId);

    List<Notification> findByMemberIdAndIsReadFalse(Long memberId);
}