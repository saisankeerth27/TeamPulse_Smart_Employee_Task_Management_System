package com.teampulse.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ChatMessageResponse {

    private Long id;

    private String sender;

    private String receiver;

    private String message;

    private LocalDateTime sentAt;

}
