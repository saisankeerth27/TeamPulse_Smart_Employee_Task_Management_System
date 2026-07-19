package com.teampulse.backend.service.impl;

import com.teampulse.backend.dto.ChatMessageRequest;
import com.teampulse.backend.dto.ChatMessageResponse;
import com.teampulse.backend.entity.ChatMessage;
import com.teampulse.backend.entity.Employee;
import com.teampulse.backend.exception.ResourceNotFoundException;
import com.teampulse.backend.repository.ChatRepository;
import com.teampulse.backend.repository.EmployeeRepository;
import com.teampulse.backend.service.ChatService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final EmployeeRepository employeeRepository;

    public ChatServiceImpl(
            ChatRepository chatRepository,
            EmployeeRepository employeeRepository) {

        this.chatRepository = chatRepository;
        this.employeeRepository = employeeRepository;

    }

    @Override
    public ChatMessageResponse sendMessage(
            ChatMessageRequest request) {

        Employee sender = employeeRepository
                .findById(request.getSenderId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Sender Not Found"));

        Employee receiver = employeeRepository
                .findById(request.getReceiverId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Receiver Not Found"));

        ChatMessage message = ChatMessage.builder()
                .sender(sender)
                .receiver(receiver)
                .message(request.getMessage())
                .isRead(false)
                .sentAt(LocalDateTime.now())
                .build();

        ChatMessage saved = chatRepository.save(message);

        return mapToResponse(saved);

    }

    @Override
    public List<ChatMessageResponse> getConversation(
            Long sender,
            Long receiver) {

        return chatRepository
                .getConversation(sender, receiver)
                .stream()
                .map(this::mapToResponse)
                .toList();

    }

    private ChatMessageResponse mapToResponse(
            ChatMessage message) {

        return ChatMessageResponse.builder()
                .id(message.getId())
                .sender(message.getSender().getFirstName())
                .receiver(message.getReceiver().getFirstName())
                .message(message.getMessage())
                .sentAt(message.getSentAt())
                .build();

    }

}
