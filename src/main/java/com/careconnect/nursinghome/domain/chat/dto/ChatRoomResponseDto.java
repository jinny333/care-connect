package com.careconnect.nursinghome.domain.chat.dto;

import com.careconnect.nursinghome.domain.chat.entity.ChatRoom;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ChatRoomResponseDto {
    private Long id;
    private Long memberIdCustomer;
    private Long memberIdFacility;
    private String lastMessage;
    private LocalDateTime createdAt;

    public ChatRoomResponseDto(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.memberIdCustomer = chatRoom.getMemberIdCustomer();
        this.memberIdFacility = chatRoom.getMemberIdFacility();
        this.lastMessage = chatRoom.getLastMessage();
        this.createdAt = chatRoom.getCreatedAt();
    }
}