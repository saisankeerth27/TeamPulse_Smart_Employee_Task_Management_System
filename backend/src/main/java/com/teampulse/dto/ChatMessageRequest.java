package com.teampulse.backend.dto;

import lombok.Data;

@Data
public class ChatMessageRequest {

    private Long senderId;

    private Long receiverId;

    private String message;

}
