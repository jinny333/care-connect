package com.careconnect.nursinghome.domain.chat.service;

import com.careconnect.nursinghome.domain.chat.dto.ChatMessageDto;
import com.careconnect.nursinghome.domain.chat.dto.ChatRoomRequestDto;
import com.careconnect.nursinghome.domain.chat.dto.ChatRoomResponseDto;
import com.careconnect.nursinghome.domain.chat.entity.ChatMessage;
import com.careconnect.nursinghome.domain.chat.entity.ChatRoom;
import com.careconnect.nursinghome.domain.chat.repository.ChatMessageRepository;
import com.careconnect.nursinghome.domain.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;

    public ChatRoomResponseDto createRoom(ChatRoomRequestDto dto) {
        ChatRoom room = ChatRoom.builder()
                .memberIdCustomer(dto.getMemberIdCustomer())
                .memberIdFacility(dto.getMemberIdFacility())
                .build();
        return new ChatRoomResponseDto(chatRoomRepository.save(room));
    }

    public List<ChatRoomResponseDto> getRooms(Long memberId) {
        return chatRoomRepository
                .findByMemberIdCustomerOrMemberIdFacility(memberId, memberId)
                .stream()
                .map(ChatRoomResponseDto::new)
                .collect(Collectors.toList());
    }

    public void deleteRoom(Long roomId) {
        chatRoomRepository.deleteById(roomId);
    }

    public ChatMessage sendMessage(ChatMessageDto dto) {
        ChatMessage message = ChatMessage.builder()
                .roomId(dto.getRoomId())
                .memberId(dto.getMemberId())
                .content(dto.getContent())
                .build();
        return chatMessageRepository.save(message);
    }

    public List<ChatMessage> getMessages(Long roomId) {
        return chatMessageRepository.findByRoomIdOrderByCreatedAtAsc(roomId);
    }
}