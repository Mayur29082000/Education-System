package com.example.education.service;

import com.example.education.entity.Department;
import java.util.List;

public interface DepartmentService {
    Department saveDepartment(Department department);
    List<Department> saveAllDepartments(List<Department> departments); // NEW: To save multiple departments
    List<Department> getAllDepartments();
    Department getDepartmentById(Long departmentId);
    Department updateDepartment(Long departmentId, Department department);
    Department patchDepartment(Long departmentId, Department department);
    Department deleteDepartment(Long departmentId);
    Department getDepartmentByName(String name);
    Department getDepartmentByCode(String code);
    List<Department> getDepartmentsByCollegeId(Long collegeId);
}