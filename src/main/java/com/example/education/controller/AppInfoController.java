package com.example.education.controller;

import com.example.education.profile.EnvironmentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class AppInfoController {

    private final EnvironmentService environmentService;

    public AppInfoController(EnvironmentService environmentService) {
        this.environmentService = environmentService;
    }

    @GetMapping("/environment")
    public String getEnvironment() {
        return environmentService.getEnvironmentMessage();
    }
}