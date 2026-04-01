package com.careconnect.nursinghome.domain.notification.controller;
import com.careconnect.nursinghome.domain.notification.dto.NotificationRequestDto;
import com.careconnect.nursinghome.domain.notification.dto.NotificationResponseDto;
import com.careconnect.nursinghome.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService service;

    @PostMapping
    public NotificationResponseDto create(@RequestBody NotificationRequestDto dto) {
        return service.create(dto);
    }

    @GetMapping("/member/{memberId}")
    public List<NotificationResponseDto> getByMember(@PathVariable Long memberId) {
        return service.getByMember(memberId);
    }

    @GetMapping("/member/{memberId}/unread")
    public List<NotificationResponseDto> getUnread(@PathVariable Long memberId) {
        return service.getUnread(memberId);
    }

    @PatchMapping("/{id}/read")
    public void read(@PathVariable Long id) {
        service.read(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}