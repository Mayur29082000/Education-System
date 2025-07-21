package com.example.education.repository;

import com.example.education.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Find departments by name
    Optional<Department> findByName(String name);

    // Find departments by code
    Optional<Department> findByCode(String code);

    // Find departments belonging to a specific college (using collegeId from the College entity)
    // Spring Data JPA traverses the relationship: Department.college.collegeId
    // --- Add or modify to this: ---
    @Query("SELECT d FROM Department d JOIN FETCH d.college WHERE d.college.collegeId = :collegeId")
    List<Department> findByCollegeCollegeId(@Param("collegeId") Long collegeId);

}