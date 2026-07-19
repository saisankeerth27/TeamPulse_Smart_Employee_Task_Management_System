package com.teampulse.backend.dto;

import com.teampulse.backend.enums.Priority;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequest {

    @NotBlank
    private String title;

    private String description;

    private Priority priority;

    private LocalDate dueDate;

    private Integer estimatedHours;

    private Long assignedEmployeeId;

    private Long departmentId;

}
