package com.careconnect.nursinghome.domain.notification.controller;

import com.careconnect.nursinghome.domain.notification.entity.Notification;
import com.careconnect.nursinghome.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService service;

    // 생성
    @PostMapping
    public Notification create(@RequestBody Notification notification) {
        return service.create(notification);
    }

    // 회원별 조회
    @GetMapping("/member/{memberId}")
    public List<Notification> getByMember(@PathVariable Long memberId) {
        return service.getByMember(memberId);
    }

    // 안 읽은 알림
    @GetMapping("/member/{memberId}/unread")
    public List<Notification> getUnread(@PathVariable Long memberId) {
        return service.getUnread(memberId);
    }

    // 읽음 처리
    @PatchMapping("/{id}/read")
    public void read(@PathVariable Long id) {
        service.read(id);
    }
}