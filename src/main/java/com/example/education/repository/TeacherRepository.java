package com.example.education.repository;

import com.example.education.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    // Find teachers by name
    Optional<Teacher> findByName(String name);

    // Find teachers by their degree
    List<Teacher> findByDegree(String degree);

    // Find teachers belonging to a specific department
    @Query("SELECT t FROM Teacher t JOIN FETCH t.department d JOIN FETCH d.college WHERE d.departmentId = :departmentId")
    List<Teacher> findByDepartmentDepartmentId(@Param("departmentId") Long departmentId);
}