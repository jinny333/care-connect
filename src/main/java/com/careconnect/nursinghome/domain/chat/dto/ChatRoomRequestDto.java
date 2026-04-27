package com.careconnect.nursinghome.domain.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomRequestDto {
    private Long memberIdCustomer;
    private Long memberIdFacility;
}