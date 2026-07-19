package com.teampulse.backend.controller;

import com.teampulse.backend.dto.AnalyticsResponse;
import com.teampulse.backend.service.AnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService service;

    public AnalyticsController(
            AnalyticsService service) {

        this.service = service;

    }

    @GetMapping
    public AnalyticsResponse analytics() {

        return service.getAnalytics();

    }

}
