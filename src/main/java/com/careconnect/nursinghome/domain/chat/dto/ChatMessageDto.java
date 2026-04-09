package com.careconnect.nursinghome.domain.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
    private Long roomId;
    private Long memberId;
    private String content;
    private String type; // ENTER, TALK, LEAVE
}