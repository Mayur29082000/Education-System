package com.example.education.service;

import com.example.education.entity.College;
import com.example.education.exception.ResourceNotFoundException;
import com.example.education.repository.CollegeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CollegeServiceImpl implements CollegeService {

    private static final Logger logger = LoggerFactory.getLogger(CollegeServiceImpl.class); // Logger instance

    private final CollegeRepository collegeRepository;

    public CollegeServiceImpl(CollegeRepository collegeRepository) {
        this.collegeRepository = collegeRepository;
    }

    @Override
    @Transactional
    public College saveCollege(College college) {
        logger.info("Saving single college: {}", college.getName());
        return collegeRepository.save(college);
    }

    @Override
    @Transactional // Ensures all saves in the list are part of one transaction
    public List<College> saveAllColleges(List<College> colleges) {
        logger.info("Saving multiple colleges. Count: {}", colleges.size());
        // No special logic needed here as College has no @ManyToOne parent
        return collegeRepository.saveAll(colleges);
    }

    @Override
    public List<College> getAllColleges() {
        logger.debug("Fetching all colleges.");
        return collegeRepository.findAll();
    }

    @Override
    public College getCollegeById(Long collegeId) {
        logger.debug("Fetching college by ID: {}", collegeId);
        return collegeRepository.findById(collegeId)
                .orElseThrow(() -> {
                    logger.warn("College not found with ID: {}", collegeId);
                    return new ResourceNotFoundException("College not found with ID: " + collegeId);
                });
    }

    @Override
    @Transactional
    public College updateCollege(Long collegeId, College college) {
        logger.info("Updating college with ID: {}", collegeId);
        College existingCollege = collegeRepository.findById(collegeId)
                .orElseThrow(() -> {
                    logger.warn("College not found for update with ID: {}", collegeId);
                    return new ResourceNotFoundException("College not found with ID: " + collegeId);
                });

        existingCollege.setName(college.getName());
        existingCollege.setAddress(college.getAddress());
        return collegeRepository.save(existingCollege);
    }

    @Override
    @Transactional
    public College patchCollege(Long collegeId, College college) {
        logger.info("Patching college with ID: {}", collegeId);
        College existingCollege = collegeRepository.findById(collegeId)
                .orElseThrow(() -> {
                    logger.warn("College not found for patch with ID: {}", collegeId);
                    return new ResourceNotFoundException("College not found with ID: " + collegeId);
                });

        if (Objects.nonNull(college.getName()) && !college.getName().isEmpty()) {
            existingCollege.setName(college.getName());
        }
        if (Objects.nonNull(college.getAddress()) && !college.getAddress().isEmpty()) {
            existingCollege.setAddress(college.getAddress());
        }
        return collegeRepository.save(existingCollege);
    }

    @Override
    @Transactional
    public College deleteCollege(Long collegeId) {
        logger.info("Deleting college with ID: {}", collegeId);
        College collegeToDelete = collegeRepository.findById(collegeId)
                .orElseThrow(() -> {
                    logger.warn("College not found for deletion with ID: {}", collegeId);
                    return new ResourceNotFoundException("College not found with ID: " + collegeId);
                });
        collegeRepository.delete(collegeToDelete);
        logger.info("Successfully deleted college with ID: {}", collegeId);
        return collegeToDelete;
    }

    @Override
    public College getCollegeByName(String name) {
        logger.debug("Fetching college by name: {}", name);
        return collegeRepository.findByName(name)
                .orElseThrow(() -> {
                    logger.warn("College not found with name: {}", name);
                    return new ResourceNotFoundException("College not found with name: " + name);
                });
    }
}