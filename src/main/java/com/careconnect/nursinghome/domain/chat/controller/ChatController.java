package com.careconnect.nursinghome.domain.chat.controller;

import com.careconnect.nursinghome.domain.chat.dto.ChatMessageDto;
import com.careconnect.nursinghome.domain.chat.dto.ChatRoomRequestDto;
import com.careconnect.nursinghome.domain.chat.dto.ChatRoomResponseDto;
import com.careconnect.nursinghome.domain.chat.entity.ChatMessage;
import com.careconnect.nursinghome.domain.chat.service.ChatService;
import com.careconnect.nursinghome.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/rooms")
    public ApiResponse<ChatRoomResponseDto> createRoom(@RequestBody ChatRoomRequestDto dto) {
        return ApiResponse.ok(chatService.createRoom(dto));
    }

    @GetMapping("/rooms")
    public ApiResponse<List<ChatRoomResponseDto>> getRooms(@RequestParam Long memberId) {
        return ApiResponse.ok(chatService.getRooms(memberId));
    }

    @DeleteMapping("/rooms/{roomId}")
    public ApiResponse<Void> deleteRoom(@PathVariable Long roomId) {
        chatService.deleteRoom(roomId);
        return ApiResponse.ok(null);
    }

    @PostMapping("/messages")
    public ApiResponse<ChatMessage> sendMessage(@RequestBody ChatMessageDto dto) {
        return ApiResponse.ok(chatService.sendMessage(dto));
    }

    @GetMapping("/rooms/{roomId}/messages")
    public ApiResponse<List<ChatMessage>> getMessages(@PathVariable Long roomId) {
        return ApiResponse.ok(chatService.getMessages(roomId));
    }
}