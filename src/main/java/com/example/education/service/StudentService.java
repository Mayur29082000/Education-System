package com.example.education.service;

import com.example.education.entity.Student;
import java.util.List;

public interface StudentService {
    Student saveStudent(Student student);
    List<Student> saveAllStudents(List<Student> students); // NEW: To save multiple students
    List<Student> getAllStudents();
    Student getStudentById(Long studentId);
    Student updateStudent(Long studentId, Student student);
    Student patchStudent(Long studentId, Student student);
    Student deleteStudent(Long studentId);
    Student getStudentByName(String name);
    Student getStudentByEmail(String email);
    List<Student> getStudentsByDepartmentId(Long departmentId);
}