package com.teampulse.backend.service;

import com.teampulse.backend.dto.NotificationResponse;

import java.util.List;

public interface NotificationService {

    List<NotificationResponse> getNotifications(Long employeeId);

    long getUnreadCount(Long employeeId);

    void markAsRead(Long notificationId);

}
