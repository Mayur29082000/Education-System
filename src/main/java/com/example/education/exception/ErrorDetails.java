package com.example.education.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// POJO to define a standard structure for error responses
@Data // Lombok for getters, setters, toString
@AllArgsConstructor // Lombok for constructor with all args
@NoArgsConstructor // Lombok for no-arg constructor
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details; // e.g., request URI or specific field errors
    private int statusCode;
}