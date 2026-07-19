package com.teampulse.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponse {

    private Long totalEmployees;

    private Long totalDepartments;

    private Long totalTasks;

    private Long completedTasks;

    private Long inProgressTasks;

    private Long todoTasks;

    private Long blockedTasks;

}
