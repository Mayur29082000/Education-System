package com.example.education.repository;

import com.example.education.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Find students by name
    Optional<Student> findByName(String name);

    // Find a student by email (assuming email is unique)
    Optional<Student> findByEmail(String email);

    // Find students belonging to a specific department

    @Query("SELECT s FROM Student s JOIN FETCH s.department d JOIN FETCH d.college WHERE d.departmentId = :departmentId")
    List<Student> findByDepartmentDepartmentId(@Param("departmentId") Long departmentId);
}