package com.example.education.service;

import com.example.education.entity.College;
import java.util.List;

public interface CollegeService {
    College saveCollege(College college);
    List<College> saveAllColleges(List<College> colleges); // NEW: To save multiple colleges
    List<College> getAllColleges();
    College getCollegeById(Long collegeId);
    College updateCollege(Long collegeId, College college);
    College patchCollege(Long collegeId, College college);
    College deleteCollege(Long collegeId);
    College getCollegeByName(String name);
}