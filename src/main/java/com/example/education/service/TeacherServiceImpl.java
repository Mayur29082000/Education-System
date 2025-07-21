package com.example.education.service;

import com.example.education.entity.Department; // Import Department entity
import com.example.education.entity.Teacher;
import com.example.education.exception.ResourceNotFoundException;
import com.example.education.repository.DepartmentRepository; // Import DepartmentRepository
import com.example.education.repository.TeacherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    private static final Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);

    private final TeacherRepository teacherRepository;
    private final DepartmentRepository departmentRepository; // Needed to fetch associated Department

    public TeacherServiceImpl(TeacherRepository teacherRepository, DepartmentRepository departmentRepository) {
        this.teacherRepository = teacherRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public Teacher saveTeacher(Teacher teacher) {
        logger.info("Saving single teacher: {}", teacher.getName());
        // Ensure the associated Department exists before saving the Teacher
        if (teacher.getDepartment() != null && teacher.getDepartment().getDepartmentId() != null) {
            Department department = departmentRepository.findById(teacher.getDepartment().getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Department not found with ID: " + teacher.getDepartment().getDepartmentId() + " for teacher " + teacher.getName()
                    ));
            teacher.setDepartment(department); // Set the managed Department entity
        } else {
            throw new IllegalArgumentException("Teacher must be associated with a valid Department ID.");
        }
        return teacherRepository.save(teacher);
    }

    @Override
    @Transactional
    public List<Teacher> saveAllTeachers(List<Teacher> teachers) {
        logger.info("Saving multiple teachers. Count: {}", teachers.size());
        for (Teacher teacher : teachers) {
            if (teacher.getDepartment() != null && teacher.getDepartment().getDepartmentId() != null) {
                Department department = departmentRepository.findById(teacher.getDepartment().getDepartmentId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Department not found with ID: " + teacher.getDepartment().getDepartmentId() + " for teacher " + teacher.getName()
                        ));
                teacher.setDepartment(department); // Set the managed Department entity
            } else {
                throw new IllegalArgumentException("Each teacher in the list must be associated with a valid Department ID.");
            }
        }
        return teacherRepository.saveAll(teachers);
    }

    @Override
    public List<Teacher> getAllTeachers() {
        logger.debug("Fetching all teachers.");
        return teacherRepository.findAll();
    }

    @Override
    public Teacher getTeacherById(Long teacherId) {
        logger.debug("Fetching teacher by ID: {}", teacherId);
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> {
                    logger.warn("Teacher not found with ID: {}", teacherId);
                    return new ResourceNotFoundException("Teacher not found with ID: " + teacherId);
                });
    }

    @Override
    @Transactional
    public Teacher updateTeacher(Long teacherId, Teacher teacher) {
        logger.info("Updating teacher with ID: {}", teacherId);
        Teacher existingTeacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> {
                    logger.warn("Teacher not found for update with ID: {}", teacherId);
                    return new ResourceNotFoundException("Teacher not found with ID: " + teacherId);
                });

        existingTeacher.setName(teacher.getName());
        existingTeacher.setDegree(teacher.getDegree());

        if (teacher.getDepartment() != null && teacher.getDepartment().getDepartmentId() != null) {
            Department department = departmentRepository.findById(teacher.getDepartment().getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Department not found with ID: " + teacher.getDepartment().getDepartmentId() + " for teacher update."
                    ));
            existingTeacher.setDepartment(department);
        } else {
            throw new IllegalArgumentException("Teacher must be associated with a valid Department ID during update.");
        }
        return teacherRepository.save(existingTeacher);
    }

    @Override
    @Transactional
    public Teacher patchTeacher(Long teacherId, Teacher teacher) {
        logger.info("Patching teacher with ID: {}", teacherId);
        Teacher existingTeacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> {
                    logger.warn("Teacher not found for patch with ID: {}", teacherId);
                    return new ResourceNotFoundException("Teacher not found with ID: " + teacherId);
                });

        if (Objects.nonNull(teacher.getName()) && !teacher.getName().isEmpty()) {
            existingTeacher.setName(teacher.getName());
        }
        if (Objects.nonNull(teacher.getDegree()) && !teacher.getDegree().isEmpty()) {
            existingTeacher.setDegree(teacher.getDegree());
        }
        if (teacher.getDepartment() != null && teacher.getDepartment().getDepartmentId() != null) {
            Department department = departmentRepository.findById(teacher.getDepartment().getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Department not found with ID: " + teacher.getDepartment().getDepartmentId() + " for teacher patch."
                    ));
            existingTeacher.setDepartment(department);
        }

        return teacherRepository.save(existingTeacher);
    }

    @Override
    @Transactional
    public Teacher deleteTeacher(Long teacherId) {
        logger.info("Deleting teacher with ID: {}", teacherId);
        Teacher teacherToDelete = teacherRepository.findById(teacherId)
                .orElseThrow(() -> {
                    logger.warn("Teacher not found for deletion with ID: {}", teacherId);
                    return new ResourceNotFoundException("Teacher not found with ID: " + teacherId);
                });
        teacherRepository.delete(teacherToDelete);
        logger.info("Successfully deleted teacher with ID: {}", teacherId);
        return teacherToDelete;
    }

    @Override
    public Teacher getTeacherByName(String name) {
        logger.debug("Fetching teacher by name: {}", name);
        return teacherRepository.findByName(name)
                .orElseThrow(() -> {
                    logger.warn("Teacher not found with name: {}", name);
                    return new ResourceNotFoundException("Teacher not found with name: " + name);
                });
    }

    @Override
    public List<Teacher> getTeachersByDegree(String degree) {
        logger.debug("Fetching teachers by degree: {}", degree);
        List<Teacher> teachers = teacherRepository.findByDegree(degree);
        if (teachers.isEmpty()) {
            logger.info("No teachers found with degree: {}", degree);
        }
        return teachers;
    }

    @Override
    public List<Teacher> getTeachersByDepartmentId(Long departmentId) {
        logger.debug("Fetching teachers by Department ID: {}", departmentId);
        List<Teacher> teachers = teacherRepository.findByDepartmentDepartmentId(departmentId);
        if (teachers.isEmpty()) {
            logger.info("No teachers found for Department ID: {}", departmentId);
        }
        return teachers;
    }
}