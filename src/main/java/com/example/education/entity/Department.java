package com.example.education.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

    @NotBlank(message = "Department name is required")
    @Size(min = 2, max = 100, message = "Department name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Department code is required")
    @Size(min = 2, max = 10, message = "Department code must be between 2 and 10 characters")
    private String code;

    // Many-to-One relationship with College
    // Multiple departments can belong to one college
    @ManyToOne(fetch = FetchType.LAZY) // Lazy fetching to avoid loading college data unless explicitly accessed
    @JoinColumn(name = "college_college_id", nullable = false) // Foreign key column in 'department' table
    @NotNull(message = "Department must be associated with a College") // Validation: Must have a college
    private College college;
}