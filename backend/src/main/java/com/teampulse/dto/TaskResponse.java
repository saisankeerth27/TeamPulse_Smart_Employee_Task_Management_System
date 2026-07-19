package com.teampulse.backend.dto;

import com.teampulse.backend.enums.Priority;
import com.teampulse.backend.enums.TaskStatus;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TaskResponse {

    private Long id;

    private String title;

    private String description;

    private Priority priority;

    private TaskStatus status;

    private LocalDate dueDate;

    private Integer estimatedHours;

    private String assignedEmployee;

    private String department;

}
