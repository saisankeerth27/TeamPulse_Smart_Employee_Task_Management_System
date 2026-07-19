package com.teampulse.backend.controller;

import com.teampulse.backend.dto.TaskRecommendationResponse;
import com.teampulse.backend.service.AiAssignmentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiAssignmentService service;

    public AiController(
            AiAssignmentService service) {

        this.service = service;

    }

    @GetMapping("/recommend/{departmentId}")
    public TaskRecommendationResponse recommend(
            @PathVariable Long departmentId) {

        return service.recommendEmployee(departmentId);

    }

}
