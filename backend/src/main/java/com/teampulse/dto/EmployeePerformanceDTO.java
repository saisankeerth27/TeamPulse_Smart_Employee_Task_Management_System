package com.teampulse.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeePerformanceDTO {

    private Long employeeId;

    private String employeeName;

    private Long completedTasks;

    private Integer workloadHours;

}
