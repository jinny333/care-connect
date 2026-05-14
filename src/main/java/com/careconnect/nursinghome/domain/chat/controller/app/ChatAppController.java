package com.careconnect.nursinghome.domain.chat.controller.app;

import com.careconnect.nursinghome.domain.chat.dto.ChatMessageDto;
import com.careconnect.nursinghome.domain.chat.dto.ChatRoomRequestDto;
import com.careconnect.nursinghome.domain.chat.dto.ChatRoomResponseDto;
import com.careconnect.nursinghome.domain.chat.entity.ChatMessage;
import com.careconnect.nursinghome.domain.chat.service.ChatService;
import com.careconnect.nursinghome.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/app/chat") // 👈 /app 추가
public class ChatAppController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    // 보호자가 채팅방 생성 (상담 시작)
    @PostMapping("/rooms")
    public ApiResponse<ChatRoomResponseDto> createRoom(
            @RequestBody ChatRoomRequestDto dto,
            Principal principal) {
        Long memberId = Long.parseLong(principal.getName());
        dto.setMemberIdCustomer(memberId);
        return ApiResponse.ok(chatService.createRoom(dto));
    }

    // 보호자의 채팅방 목록 조회
    @GetMapping("/rooms")
    public ApiResponse<List<ChatRoomResponseDto>> getRooms(Principal principal) {
        Long memberId = Long.parseLong(principal.getName());
        return ApiResponse.ok(chatService.getRooms(memberId));
    }

    // 과거 메시지 내역 조회
    @GetMapping("/rooms/{roomId}/messages")
    public ApiResponse<List<ChatMessage>> getMessages(@PathVariable Long roomId) {
        return ApiResponse.ok(chatService.getMessages(roomId));
    }

    // WebSocket 메시지 전송 (실시간)
    @MessageMapping("/chat/message")
    public void sendMessageWebSocket(ChatMessageDto dto, Principal principal) {
        Long memberId = Long.parseLong(principal.getName());
        dto.setMemberId(memberId);
        chatService.sendMessage(dto);
        messagingTemplate.convertAndSend("/sub/chat/room/" + dto.getRoomId(), dto);
    }
}