package com.teampulse.backend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AnalyticsResponse {

    private Long totalEmployees;

    private Long totalDepartments;

    private Long totalTasks;

    private Double completionRate;

    private Double averageCompletionHours;

    private List<EmployeePerformanceDTO> topEmployees;

}
