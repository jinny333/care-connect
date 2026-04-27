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

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/rooms")
    public ApiResponse<ChatRoomResponseDto> createRoom(
            @RequestBody ChatRoomRequestDto dto,
            Principal principal) {
        Long memberId = Long.parseLong(principal.getName());
        dto.setMemberIdCustomer(memberId);
        return ApiResponse.ok(chatService.createRoom(dto));
    }

    @GetMapping("/rooms")
    public ApiResponse<List<ChatRoomResponseDto>> getRooms(Principal principal) {
        Long memberId = Long.parseLong(principal.getName());
        return ApiResponse.ok(chatService.getRooms(memberId));
    }

    @DeleteMapping("/rooms/{roomId}")
    public ApiResponse<Void> deleteRoom(@PathVariable Long roomId) {
        chatService.deleteRoom(roomId);
        return ApiResponse.ok(null);
    }

    @PostMapping("/messages")
    public ApiResponse<ChatMessage> sendMessageRest(
            @RequestBody ChatMessageDto dto,
            Principal principal) {
        Long memberId = Long.parseLong(principal.getName());
        dto.setMemberId(memberId);
        return ApiResponse.ok(chatService.sendMessage(dto));
    }

    @GetMapping("/rooms/{roomId}/messages")
    public ApiResponse<List<ChatMessage>> getMessages(@PathVariable Long roomId) {
        return ApiResponse.ok(chatService.getMessages(roomId));
    }

    @MessageMapping("/chat/message")
    public void sendMessageWebSocket(ChatMessageDto dto, Principal principal) {
        Long memberId = Long.parseLong(principal.getName());
        dto.setMemberId(memberId);
        chatService.sendMessage(dto);
        messagingTemplate.convertAndSend("/sub/chat/room/" + dto.getRoomId(), dto);
    }
}