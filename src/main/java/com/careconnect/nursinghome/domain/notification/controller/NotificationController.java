package com.careconnect.nursinghome.domain.notification.controller;

import com.careconnect.nursinghome.domain.notification.dto.NotificationRequestDto;
import com.careconnect.nursinghome.domain.notification.dto.NotificationResponseDto;
import com.careconnect.nursinghome.domain.notification.service.NotificationService;
import com.careconnect.nursinghome.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService service;

    @PostMapping
    public ApiResponse<NotificationResponseDto> create(@RequestBody NotificationRequestDto dto) {
        return ApiResponse.ok(service.create(dto));
    }

    @GetMapping("/member/{memberId}")
    public ApiResponse<List<NotificationResponseDto>> getByMember(@PathVariable Long memberId) {
        return ApiResponse.ok(service.getByMember(memberId));
    }

    @GetMapping("/member/{memberId}/unread")
    public ApiResponse<List<NotificationResponseDto>> getUnread(@PathVariable Long memberId) {
        return ApiResponse.ok(service.getUnread(memberId));
    }

    @PatchMapping("/{id}/read")
    public ApiResponse<Void> read(@PathVariable Long id) {
        service.read(id);
        return ApiResponse.ok(null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok(null);
    }
}