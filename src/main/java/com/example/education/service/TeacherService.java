package com.example.education.service;

import com.example.education.entity.Teacher;
import java.util.List;

public interface TeacherService {
    Teacher saveTeacher(Teacher teacher);
    List<Teacher> saveAllTeachers(List<Teacher> teachers); // NEW: To save multiple teachers
    List<Teacher> getAllTeachers();
    Teacher getTeacherById(Long teacherId);
    Teacher updateTeacher(Long teacherId, Teacher teacher);
    Teacher patchTeacher(Long teacherId, Teacher teacher);
    Teacher deleteTeacher(Long teacherId);
    Teacher getTeacherByName(String name);
    List<Teacher> getTeachersByDegree(String degree);
    List<Teacher> getTeachersByDepartmentId(Long departmentId);
}