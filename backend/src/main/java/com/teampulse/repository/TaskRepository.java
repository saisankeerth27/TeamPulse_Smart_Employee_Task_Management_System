package com.teampulse.backend.repository;

import com.teampulse.backend.entity.Task;
import com.teampulse.backend.enums.Priority;
import com.teampulse.backend.enums.TaskStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository
        extends JpaRepository<Task, Long> {

    List<Task> findByAssignedEmployeeId(Long employeeId);

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByPriority(Priority priority);

}
