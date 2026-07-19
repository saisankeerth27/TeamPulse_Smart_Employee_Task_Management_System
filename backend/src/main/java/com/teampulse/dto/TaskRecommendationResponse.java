package com.teampulse.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskRecommendationResponse {

    private Long employeeId;

    private String employeeName;

    private String department;

    private Integer activeTasks;

    private Integer workloadHours;

    private Double score;

    private String reason;

}
