package com.careconnect.nursinghome.domain.chat.dto;

import lombok.Getter;

@Getter
public class ChatMessageDto {
    private Long roomId;
    private Long memberId;
    private String content;
}