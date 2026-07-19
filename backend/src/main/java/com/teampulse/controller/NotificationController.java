package com.teampulse.backend.controller;

import com.teampulse.backend.dto.NotificationResponse;
import com.teampulse.backend.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(
            NotificationService service) {

        this.service = service;

    }

    @GetMapping("/{employeeId}")
    public List<NotificationResponse> getNotifications(
            @PathVariable Long employeeId) {

        return service.getNotifications(employeeId);

    }

    @GetMapping("/{employeeId}/count")
    public long unreadCount(
            @PathVariable Long employeeId) {

        return service.getUnreadCount(employeeId);

    }

    @PatchMapping("/{id}")
    public void markRead(
            @PathVariable Long id) {

        service.markAsRead(id);

    }

}
