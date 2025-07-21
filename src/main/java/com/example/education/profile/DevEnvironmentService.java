package com.example.education.profile;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component // Marks this as a Spring component
@Profile("dev") // This bean will only be active when the 'dev' profile is active
public class DevEnvironmentService implements EnvironmentService {
    @Override
    public String getEnvironmentMessage() {
        return "You are in the Development Environment!";
    }
}