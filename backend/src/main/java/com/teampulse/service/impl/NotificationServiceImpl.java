package com.teampulse.backend.service.impl;

import com.teampulse.backend.dto.NotificationResponse;
import com.teampulse.backend.entity.Notification;
import com.teampulse.backend.exception.ResourceNotFoundException;
import com.teampulse.backend.repository.NotificationRepository;
import com.teampulse.backend.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl
        implements NotificationService {

    private final NotificationRepository repository;

    public NotificationServiceImpl(
            NotificationRepository repository) {

        this.repository = repository;

    }

    @Override
    public List<NotificationResponse> getNotifications(
            Long employeeId) {

        return repository
                .findByEmployeeIdOrderByCreatedAtDesc(employeeId)
                .stream()
                .map(this::mapToResponse)
                .toList();

    }

    @Override
    public long getUnreadCount(Long employeeId) {

        return repository
                .countByEmployeeIdAndIsReadFalse(employeeId);

    }

    @Override
    public void markAsRead(Long notificationId) {

        Notification notification = repository
                .findById(notificationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Notification Not Found"));

        notification.setIsRead(true);

        repository.save(notification);

    }

    private NotificationResponse mapToResponse(
            Notification notification) {

        return NotificationResponse.builder()
                .id(notification.getId())
                .title(notification.getTitle())
                .message(notification.getMessage())
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedAt())
                .build();

    }

}
