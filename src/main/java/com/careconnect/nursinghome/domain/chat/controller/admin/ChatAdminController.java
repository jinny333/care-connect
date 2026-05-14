package com.careconnect.nursinghome.domain.chat.controller.admin;

import com.careconnect.nursinghome.domain.chat.dto.ChatRoomResponseDto;
import com.careconnect.nursinghome.domain.chat.entity.ChatMessage;
import com.careconnect.nursinghome.domain.chat.service.ChatService;
import com.careconnect.nursinghome.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/chat") // 👈 /admin 추가
public class ChatAdminController {

    private final ChatService chatService;

    // 우리 요양원에 들어온 모든 채팅 상담 목록 조회
    @GetMapping("/rooms")
    public ApiResponse<List<ChatRoomResponseDto>> getFacilityRooms(Principal principal) {
        Long memberId = Long.parseLong(principal.getName()); // 요양원 관리자의 ID
        return ApiResponse.ok(chatService.getRooms(memberId));
    }

    // 상담방 상세 메시지 내역 확인
    @GetMapping("/rooms/{roomId}/messages")
    public ApiResponse<List<ChatMessage>> getMessages(@PathVariable Long roomId) {
        return ApiResponse.ok(chatService.getMessages(roomId));
    }

    // 채팅방 강제 종료/삭제 (관리자 권한)
    @DeleteMapping("/rooms/{roomId}")
    public ApiResponse<Void> deleteRoom(@PathVariable Long roomId) {
        chatService.deleteRoom(roomId);
        return ApiResponse.ok(null);
    }
}