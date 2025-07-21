package com.example.education.service;

import com.example.education.entity.College; // Import College entity
import com.example.education.entity.Department;
import com.example.education.exception.ResourceNotFoundException;
import com.example.education.repository.CollegeRepository; // Import CollegeRepository
import com.example.education.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    private final DepartmentRepository departmentRepository;
    private final CollegeRepository collegeRepository; // Needed to fetch associated College

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, CollegeRepository collegeRepository) {
        this.departmentRepository = departmentRepository;
        this.collegeRepository = collegeRepository;
    }

    @Override
    @Transactional
    public Department saveDepartment(Department department) {
        logger.info("Saving single department: {}", department.getName());
        // Ensure the associated College exists before saving the Department
        if (department.getCollege() != null && department.getCollege().getCollegeId() != null) {
            College college = collegeRepository.findById(department.getCollege().getCollegeId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "College not found with ID: " + department.getCollege().getCollegeId() + " for department " + department.getName()
                    ));
            department.setCollege(college); // Set the managed College entity
        } else {
            throw new IllegalArgumentException("Department must be associated with a valid College ID.");
        }
        return departmentRepository.save(department);
    }

    @Override
    @Transactional
    public List<Department> saveAllDepartments(List<Department> departments) {
        logger.info("Saving multiple departments. Count: {}", departments.size());
        for (Department dept : departments) {
            if (dept.getCollege() != null && dept.getCollege().getCollegeId() != null) {
                College college = collegeRepository.findById(dept.getCollege().getCollegeId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "College not found with ID: " + dept.getCollege().getCollegeId() + " for department " + dept.getName()
                        ));
                dept.setCollege(college); // Set the managed College entity
            } else {
                throw new IllegalArgumentException("Each department in the list must be associated with a valid College ID.");
            }
        }
        return departmentRepository.saveAll(departments);
    }

    @Override
    public List<Department> getAllDepartments() {
        logger.debug("Fetching all departments.");
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(Long departmentId) {
        logger.debug("Fetching department by ID: {}", departmentId);
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> {
                    logger.warn("Department not found with ID: {}", departmentId);
                    return new ResourceNotFoundException("Department not found with ID: " + departmentId);
                });
    }

    @Override
    @Transactional
    public Department updateDepartment(Long departmentId, Department department) {
        logger.info("Updating department with ID: {}", departmentId);
        Department existingDepartment = departmentRepository.findById(departmentId)
                .orElseThrow(() -> {
                    logger.warn("Department not found for update with ID: {}", departmentId);
                    return new ResourceNotFoundException("Department not found with ID: " + departmentId);
                });

        existingDepartment.setName(department.getName());
        existingDepartment.setCode(department.getCode());

        // Update associated college if provided and valid
        if (department.getCollege() != null && department.getCollege().getCollegeId() != null) {
            College college = collegeRepository.findById(department.getCollege().getCollegeId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "College not found with ID: " + department.getCollege().getCollegeId() + " for department update."
                    ));
            existingDepartment.setCollege(college); // Set managed college entity
        } else {
            // Decide how to handle if college is intentionally unlinked or null
            // For a PUT, it's generally expected all fields, including associations, are provided.
            throw new IllegalArgumentException("Department must be associated with a valid College ID during update.");
        }
        return departmentRepository.save(existingDepartment);
    }

    @Override
    @Transactional
    public Department patchDepartment(Long departmentId, Department department) {
        logger.info("Patching department with ID: {}", departmentId);
        Department existingDepartment = departmentRepository.findById(departmentId)
                .orElseThrow(() -> {
                    logger.warn("Department not found for patch with ID: {}", departmentId);
                    return new ResourceNotFoundException("Department not found with ID: " + departmentId);
                });

        if (Objects.nonNull(department.getName()) && !department.getName().isEmpty()) {
            existingDepartment.setName(department.getName());
        }
        if (Objects.nonNull(department.getCode()) && !department.getCode().isEmpty()) {
            existingDepartment.setCode(department.getCode());
        }
        // Patch associated college if provided and valid
        if (department.getCollege() != null && department.getCollege().getCollegeId() != null) {
            College college = collegeRepository.findById(department.getCollege().getCollegeId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "College not found with ID: " + department.getCollege().getCollegeId() + " for department patch."
                    ));
            existingDepartment.setCollege(college);
        } // If college is null in patch request, it means not to change the association

        return departmentRepository.save(existingDepartment);
    }

    @Override
    @Transactional
    public Department deleteDepartment(Long departmentId) {
        logger.info("Deleting department with ID: {}", departmentId);
        Department departmentToDelete = departmentRepository.findById(departmentId)
                .orElseThrow(() -> {
                    logger.warn("Department not found for deletion with ID: {}", departmentId);
                    return new ResourceNotFoundException("Department not found with ID: " + departmentId);
                });
        departmentRepository.delete(departmentToDelete);
        logger.info("Successfully deleted department with ID: {}", departmentId);
        return departmentToDelete;
    }

    @Override
    public Department getDepartmentByName(String name) {
        logger.debug("Fetching department by name: {}", name);
        return departmentRepository.findByName(name)
                .orElseThrow(() -> {
                    logger.warn("Department not found with name: {}", name);
                    return new ResourceNotFoundException("Department not found with name: " + name);
                });
    }

    @Override
    public Department getDepartmentByCode(String code) {
        logger.debug("Fetching department by code: {}", code);
        return departmentRepository.findByCode(code)
                .orElseThrow(() -> {
                    logger.warn("Department not found with code: {}", code);
                    return new ResourceNotFoundException("Department not found with code: " + code);
                });
    }

    @Override
    public List<Department> getDepartmentsByCollegeId(Long collegeId) {
        logger.debug("Fetching departments by College ID: {}", collegeId);
        List<Department> departments = departmentRepository.findByCollegeCollegeId(collegeId);
        if (departments.isEmpty()) {
            logger.info("No departments found for College ID: {}", collegeId);
            // Optional: throw ResourceNotFoundException here if you expect at least one
            // throw new ResourceNotFoundException("No departments found for College ID: " + collegeId);
        }
        return departments;
    }
}