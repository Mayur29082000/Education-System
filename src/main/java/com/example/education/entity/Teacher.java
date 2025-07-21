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
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacherId;

    @NotBlank(message = "Teacher name is required")
    @Size(min = 2, max = 100, message = "Teacher name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Teacher degree is required")
    @Size(min = 2, max = 50, message = "Teacher degree must be between 2 and 50 characters")
    private String degree; // e.g., "Ph.D. CS", "M.Tech ENTC"

    // Many-to-One relationship with Department
    // Multiple teachers can belong to one department
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_department_id", nullable = false) // Foreign key column in 'teacher' table
    @NotNull(message = "Teacher must be associated with a Department") // Validation: Must have a department
    private Department department;
}