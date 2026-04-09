package com.careconnect.nursinghome.domain.chat.controller;

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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    // 채팅방 생성
    @PostMapping("/rooms")
    public ApiResponse<ChatRoomResponseDto> createRoom(@RequestBody ChatRoomRequestDto dto) {
        return ApiResponse.ok(chatService.createRoom(dto));
    }

    // 내 채팅방 목록 조회
    @GetMapping("/rooms")
    public ApiResponse<List<ChatRoomResponseDto>> getRooms(@RequestParam Long memberId) {
        return ApiResponse.ok(chatService.getRooms(memberId));
    }

    // 채팅방 나가기
    @DeleteMapping("/rooms/{roomId}")
    public ApiResponse<Void> deleteRoom(@PathVariable Long roomId) {
        chatService.deleteRoom(roomId);
        return ApiResponse.ok(null);
    }

    // REST API 메시지 전송
    @PostMapping("/messages")
    public ApiResponse<ChatMessage> sendMessageRest(@RequestBody ChatMessageDto dto) {
        return ApiResponse.ok(chatService.sendMessage(dto));
    }

    // 메시지 조회
    @GetMapping("/rooms/{roomId}/messages")
    public ApiResponse<List<ChatMessage>> getMessages(@PathVariable Long roomId) {
        return ApiResponse.ok(chatService.getMessages(roomId));
    }

    // WebSocket 실시간 메시지 송수신
    @MessageMapping("/chat/message")
    public void sendMessageWebSocket(ChatMessageDto dto) {
        chatService.sendMessage(dto);
        messagingTemplate.convertAndSend("/sub/chat/room/" + dto.getRoomId(), dto);
    }
}