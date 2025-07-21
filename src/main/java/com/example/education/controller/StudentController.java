package com.example.education.controller;

import com.example.education.entity.Student;
import com.example.education.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> saveStudent(@Valid @RequestBody Student student) {
        Student savedStudent = studentService.saveStudent(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @PostMapping("/batch") // NEW: Endpoint to save multiple students
    public ResponseEntity<List<Student>> saveAllStudents(@Valid @RequestBody List<Student> students) {
        List<Student> savedStudents = studentService.saveAllStudents(students);
        return new ResponseEntity<>(savedStudents, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long studentId) {
        Student student = studentService.getStudentById(studentId);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Student> getStudentByName(@PathVariable("name") String name) {
        Student student = studentService.getStudentByName(name);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Student> getStudentByEmail(@PathVariable("email") String email) {
        Student student = studentService.getStudentByEmail(email);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Student>> getStudentsByDepartmentId(@PathVariable("departmentId") Long departmentId) {
        List<Student> students = studentService.getStudentsByDepartmentId(departmentId);
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") Long studentId, @Valid @RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(studentId, student);
        return ResponseEntity.ok(updatedStudent);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Student> patchStudent(@PathVariable("id") Long studentId, @RequestBody Student student) {
        Student patchedStudent = studentService.patchStudent(studentId, student);
        return ResponseEntity.ok(patchedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable("id") Long studentId) {
        Student deletedStudent = studentService.deleteStudent(studentId);
        return ResponseEntity.ok(deletedStudent);
    }
}