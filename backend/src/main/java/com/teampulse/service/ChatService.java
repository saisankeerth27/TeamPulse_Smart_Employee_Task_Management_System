package com.teampulse.backend.service;

import com.teampulse.backend.dto.ChatMessageRequest;
import com.teampulse.backend.dto.ChatMessageResponse;

import java.util.List;

public interface ChatService {

    ChatMessageResponse sendMessage(
            ChatMessageRequest request);

    List<ChatMessageResponse> getConversation(
            Long sender,
            Long receiver);

}
