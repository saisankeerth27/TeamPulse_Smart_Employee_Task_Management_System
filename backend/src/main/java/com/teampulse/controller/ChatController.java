package com.teampulse.backend.controller;

import com.teampulse.backend.dto.ChatMessageRequest;
import com.teampulse.backend.dto.ChatMessageResponse;
import com.teampulse.backend.service.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(
            ChatService chatService,
            SimpMessagingTemplate messagingTemplate) {

        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;

    }

    @MessageMapping("/send")
    public void sendMessage(
            @Payload ChatMessageRequest request) {

        ChatMessageResponse response =
                chatService.sendMessage(request);

        messagingTemplate.convertAndSendToUser(
                request.getReceiverId().toString(),
                "/queue/messages",
                response
        );

        messagingTemplate.convertAndSendToUser(
                request.getSenderId().toString(),
                "/queue/messages",
                response
        );

    }

}
