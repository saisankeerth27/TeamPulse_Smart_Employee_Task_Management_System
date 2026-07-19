package com.teampulse.backend.service;

import com.teampulse.backend.dto.TaskRecommendationResponse;

public interface AiAssignmentService {

    TaskRecommendationResponse recommendEmployee(
            Long departmentId);

}
