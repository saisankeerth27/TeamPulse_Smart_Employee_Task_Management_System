package com.teampulse.backend.controller;

import com.teampulse.backend.dto.ChatMessageRequest;
import com.teampulse.backend.dto.ChatMessageResponse;
import com.teampulse.backend.service.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatMessageController {

    private final ChatService chatService;

    public ChatMessageController(
            ChatService chatService) {

        this.chatService = chatService;

    }

    @PostMapping("/send")
    public ChatMessageResponse sendMessage(
            @RequestBody ChatMessageRequest request) {

        return chatService.sendMessage(request);

    }

    @GetMapping("/{sender}/{receiver}")
    public List<ChatMessageResponse> getConversation(
            @PathVariable Long sender,
            @PathVariable Long receiver) {

        return chatService.getConversation(sender, receiver);

    }

}
