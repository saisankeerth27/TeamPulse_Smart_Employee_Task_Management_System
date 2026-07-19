package com.teampulse.backend.repository;

import com.teampulse.backend.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository
        extends JpaRepository<Notification, Long> {

    List<Notification>
    findByEmployeeIdOrderByCreatedAtDesc(Long id);

    long countByEmployeeIdAndIsReadFalse(Long id);

}
