package com.example.education.controller;

import com.example.education.entity.College;
import com.example.education.service.CollegeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/colleges")
public class CollegeController {

    private final CollegeService collegeService;

    public CollegeController(CollegeService collegeService) {
        this.collegeService = collegeService;
    }

    @PostMapping // Endpoint to save a single college
    public ResponseEntity<College> saveCollege(@Valid @RequestBody College college) {
        College savedCollege = collegeService.saveCollege(college);
        return new ResponseEntity<>(savedCollege, HttpStatus.CREATED); // Returns 201 Created
    }

    @PostMapping("/batch") // NEW: Endpoint to save multiple colleges
    public ResponseEntity<List<College>> saveAllColleges(@Valid @RequestBody List<College> colleges) {
        List<College> savedColleges = collegeService.saveAllColleges(colleges);
        return new ResponseEntity<>(savedColleges, HttpStatus.CREATED); // Returns 201 Created
    }

    @GetMapping
    public ResponseEntity<List<College>> getAllColleges() {
        List<College> colleges = collegeService.getAllColleges();
        return ResponseEntity.ok(colleges); // Returns 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<College> getCollegeById(@PathVariable("id") Long collegeId) {
        College college = collegeService.getCollegeById(collegeId);
        return ResponseEntity.ok(college);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<College> getCollegeByName(@PathVariable("name") String name) {
        College college = collegeService.getCollegeByName(name);
        return ResponseEntity.ok(college);
    }

    @PutMapping("/{id}")
    public ResponseEntity<College> updateCollege(@PathVariable("id") Long collegeId, @Valid @RequestBody College college) {
        College updatedCollege = collegeService.updateCollege(collegeId, college);
        return ResponseEntity.ok(updatedCollege);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<College> patchCollege(@PathVariable("id") Long collegeId, @RequestBody College college) {
        College patchedCollege = collegeService.patchCollege(collegeId, college);
        return ResponseEntity.ok(patchedCollege);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<College> deleteCollege(@PathVariable("id") Long collegeId) {
        College deletedCollege = collegeService.deleteCollege(collegeId);
        return ResponseEntity.ok(deletedCollege); // Or ResponseEntity.noContent().build() for 204
    }
}