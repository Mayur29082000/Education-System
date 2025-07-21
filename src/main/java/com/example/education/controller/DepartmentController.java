package com.example.education.controller;

import com.example.education.entity.Department;
import com.example.education.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<Department> saveDepartment(@Valid @RequestBody Department department) {
        Department savedDepartment = departmentService.saveDepartment(department);
        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

    @PostMapping("/batch") // NEW: Endpoint to save multiple departments
    public ResponseEntity<List<Department>> saveAllDepartments(@Valid @RequestBody List<Department> departments) {
        List<Department> savedDepartments = departmentService.saveAllDepartments(departments);
        return new ResponseEntity<>(savedDepartments, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable("id") Long departmentId) {
        Department department = departmentService.getDepartmentById(departmentId);
        return ResponseEntity.ok(department);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Department> getDepartmentByName(@PathVariable("name") String name) {
        Department department = departmentService.getDepartmentByName(name);
        return ResponseEntity.ok(department);
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<Department> getDepartmentByCode(@PathVariable("code") String code) {
        Department department = departmentService.getDepartmentByCode(code);
        return ResponseEntity.ok(department);
    }

    @GetMapping("/college/{collegeId}")
    public ResponseEntity<List<Department>> getDepartmentsByCollegeId(@PathVariable("collegeId") Long collegeId) {
        List<Department> departments = departmentService.getDepartmentsByCollegeId(collegeId);
        return ResponseEntity.ok(departments);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable("id") Long departmentId, @Valid @RequestBody Department department) {
        Department updatedDepartment = departmentService.updateDepartment(departmentId, department);
        return ResponseEntity.ok(updatedDepartment);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Department> patchDepartment(@PathVariable("id") Long departmentId, @RequestBody Department department) {
        Department patchedDepartment = departmentService.patchDepartment(departmentId, department);
        return ResponseEntity.ok(patchedDepartment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Department> deleteDepartment(@PathVariable("id") Long departmentId) {
        Department deletedDepartment = departmentService.deleteDepartment(departmentId);
        return ResponseEntity.ok(deletedDepartment);
    }
}