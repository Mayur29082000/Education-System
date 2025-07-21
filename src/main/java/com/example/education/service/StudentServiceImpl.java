package com.example.education.service;

import com.example.education.entity.Department; // Import Department entity
import com.example.education.entity.Student;
import com.example.education.exception.ResourceNotFoundException;
import com.example.education.repository.DepartmentRepository; // Import DepartmentRepository
import com.example.education.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository; // Needed to fetch associated Department

    public StudentServiceImpl(StudentRepository studentRepository, DepartmentRepository departmentRepository) {
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public Student saveStudent(Student student) {
        logger.info("Saving single student: {}", student.getName());
        // Ensure the associated Department exists before saving the Student
        if (student.getDepartment() != null && student.getDepartment().getDepartmentId() != null) {
            Department department = departmentRepository.findById(student.getDepartment().getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Department not found with ID: " + student.getDepartment().getDepartmentId() + " for student " + student.getName()
                    ));
            student.setDepartment(department); // Set the managed Department entity
        } else {
            throw new IllegalArgumentException("Student must be associated with a valid Department ID.");
        }
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public List<Student> saveAllStudents(List<Student> students) {
        logger.info("Saving multiple students. Count: {}", students.size());
        for (Student student : students) {
            if (student.getDepartment() != null && student.getDepartment().getDepartmentId() != null) {
                Department department = departmentRepository.findById(student.getDepartment().getDepartmentId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Department not found with ID: " + student.getDepartment().getDepartmentId() + " for student " + student.getName()
                        ));
                student.setDepartment(department); // Set the managed Department entity
            } else {
                throw new IllegalArgumentException("Each student in the list must be associated with a valid Department ID.");
            }
        }
        return studentRepository.saveAll(students);
    }

    @Override
    public List<Student> getAllStudents() {
        logger.debug("Fetching all students.");
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long studentId) {
        logger.debug("Fetching student by ID: {}", studentId);
        return studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    logger.warn("Student not found with ID: {}", studentId);
                    return new ResourceNotFoundException("Student not found with ID: " + studentId);
                });
    }

    @Override
    @Transactional
    public Student updateStudent(Long studentId, Student student) {
        logger.info("Updating student with ID: {}", studentId);
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    logger.warn("Student not found for update with ID: {}", studentId);
                    return new ResourceNotFoundException("Student not found with ID: " + studentId);
                });

        existingStudent.setName(student.getName());
        existingStudent.setEmail(student.getEmail());

        if (student.getDepartment() != null && student.getDepartment().getDepartmentId() != null) {
            Department department = departmentRepository.findById(student.getDepartment().getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Department not found with ID: " + student.getDepartment().getDepartmentId() + " for student update."
                    ));
            existingStudent.setDepartment(department);
        } else {
            throw new IllegalArgumentException("Student must be associated with a valid Department ID during update.");
        }
        return studentRepository.save(existingStudent);
    }

    @Override
    @Transactional
    public Student patchStudent(Long studentId, Student student) {
        logger.info("Patching student with ID: {}", studentId);
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    logger.warn("Student not found for patch with ID: {}", studentId);
                    return new ResourceNotFoundException("Student not found with ID: " + studentId);
                });

        if (Objects.nonNull(student.getName()) && !student.getName().isEmpty()) {
            existingStudent.setName(student.getName());
        }
        if (Objects.nonNull(student.getEmail()) && !student.getEmail().isEmpty()) {
            existingStudent.setEmail(student.getEmail());
        }
        if (student.getDepartment() != null && student.getDepartment().getDepartmentId() != null) {
            Department department = departmentRepository.findById(student.getDepartment().getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Department not found with ID: " + student.getDepartment().getDepartmentId() + " for student patch."
                    ));
            existingStudent.setDepartment(department);
        }

        return studentRepository.save(existingStudent);
    }

    @Override
    @Transactional
    public Student deleteStudent(Long studentId) {
        logger.info("Deleting student with ID: {}", studentId);
        Student studentToDelete = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    logger.warn("Student not found for deletion with ID: {}", studentId);
                    return new ResourceNotFoundException("Student not found with ID: " + studentId);
                });
        studentRepository.delete(studentToDelete);
        logger.info("Successfully deleted student with ID: {}", studentId);
        return studentToDelete;
    }

    @Override
    public Student getStudentByName(String name) {
        logger.debug("Fetching student by name: {}", name);
        return studentRepository.findByName(name)
                .orElseThrow(() -> {
                    logger.warn("Student not found with name: {}", name);
                    return new ResourceNotFoundException("Student not found with name: " + name);
                });
    }

    @Override
    public Student getStudentByEmail(String email) {
        logger.debug("Fetching student by email: {}", email);
        return studentRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.warn("Student not found with email: {}", email);
                    return new ResourceNotFoundException("Student not found with email: " + email);
                });
    }

    @Override
    public List<Student> getStudentsByDepartmentId(Long departmentId) {
        logger.debug("Fetching students by Department ID: {}", departmentId);
        List<Student> students = studentRepository.findByDepartmentDepartmentId(departmentId);
        if (students.isEmpty()) {
            logger.info("No students found for Department ID: {}", departmentId);
        }
        return students;
    }
}