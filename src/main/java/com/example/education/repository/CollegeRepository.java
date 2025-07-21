package com.example.education.repository;

import com.example.education.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Marks this interface as a Spring Data JPA repository
public interface CollegeRepository extends JpaRepository<College, Long> {
    // JpaRepository provides basic CRUD operations: save, findById, findAll, deleteById, etc.

    // Custom derived query method: find a college by its name
    // Spring Data JPA automatically generates the query: SELECT c FROM College c WHERE c.name = ?1
    Optional<College> findByName(String name);

    // Custom derived query method: find a college by its address
    Optional<College> findByAddress(String address);
}