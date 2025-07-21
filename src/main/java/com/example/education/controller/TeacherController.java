package com.example.education.controller;

import com.example.education.entity.Teacher;
import com.example.education.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    public ResponseEntity<Teacher> saveTeacher(@Valid @RequestBody Teacher teacher) {
        Teacher savedTeacher = teacherService.saveTeacher(teacher);
        return new ResponseEntity<>(savedTeacher, HttpStatus.CREATED);
    }

    @PostMapping("/batch") // NEW: Endpoint to save multiple teachers
    public ResponseEntity<List<Teacher>> saveAllTeachers(@Valid @RequestBody List<Teacher> teachers) {
        List<Teacher> savedTeachers = teacherService.saveAllTeachers(teachers);
        return new ResponseEntity<>(savedTeachers, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable("id") Long teacherId) {
        Teacher teacher = teacherService.getTeacherById(teacherId);
        return ResponseEntity.ok(teacher);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Teacher> getTeacherByName(@PathVariable("name") String name) {
        Teacher teacher = teacherService.getTeacherByName(name);
        return ResponseEntity.ok(teacher);
    }

    @GetMapping("/degree/{degree}")
    public ResponseEntity<List<Teacher>> getTeachersByDegree(@PathVariable("degree") String degree) {
        List<Teacher> teachers = teacherService.getTeachersByDegree(degree);
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Teacher>> getTeachersByDepartmentId(@PathVariable("departmentId") Long departmentId) {
        List<Teacher> teachers = teacherService.getTeachersByDepartmentId(departmentId);
        return ResponseEntity.ok(teachers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable("id") Long teacherId, @Valid @RequestBody Teacher teacher) {
        Teacher updatedTeacher = teacherService.updateTeacher(teacherId, teacher);
        return ResponseEntity.ok(updatedTeacher);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Teacher> patchTeacher(@PathVariable("id") Long teacherId, @RequestBody Teacher teacher) {
        Teacher patchedTeacher = teacherService.patchTeacher(teacherId, teacher);
        return ResponseEntity.ok(patchedTeacher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Teacher> deleteTeacher(@PathVariable("id") Long teacherId) {
        Teacher deletedTeacher = teacherService.deleteTeacher(teacherId);
        return ResponseEntity.ok(deletedTeacher);
    }
}