package com.example.education.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @NotBlank(message = "Student name is required")
    @Size(min = 2, max = 100, message = "Student name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Student email is required")
    @Email(message = "Email should be valid") // Validation: Email format
    @Column(unique = true) // Database constraint: email must be unique
    private String email;

    // Many-to-One relationship with Department
    // Multiple students can belong to one department
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_department_id", nullable = false) // Foreign key column in 'student' table
    @NotNull(message = "Student must be associated with a Department") // Validation: Must have a department
    private Department department;
}