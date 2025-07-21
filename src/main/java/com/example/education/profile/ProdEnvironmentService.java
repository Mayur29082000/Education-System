package com.example.education.profile;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component // Marks this as a Spring component
@Profile("prod") // This bean will only be active when the 'prod' profile is active
public class ProdEnvironmentService implements EnvironmentService {
    @Override
    public String getEnvironmentMessage() {
        return "You are in the Production Environment. Be careful!";
    }
}