package com.example.education.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "college")
public class College {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long collegeId;

    @NotBlank(message = "College name is required")
    @Size(min = 2, max = 100, message = "College name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "College address is required")
    @Size(min = 5, max = 255, message = "College address must be between 5 and 255 characters")
    private String address;

    // @OneToMany relationship is typically managed from the "many" side (Department)
    // For simplicity in this example, we won't add the @OneToMany here,
    // as it can lead to N+1 problems and bidirectional relationship complexities
    // unless carefully managed (e.g., using DTOs, @JsonManagedReference/@JsonBackReference).
    // The relationship is established via the @ManyToOne in Department.
}