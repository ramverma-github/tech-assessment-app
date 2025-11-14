package com.tech.assessment.controller;

import com.tech.assessment.dto.TestDto;
import com.tech.assessment.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/assessments")
@CrossOrigin(origins = "http://localhost:3000")
public class AssessmentController {

    private final TestService testService;

    public AssessmentController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping
    @Operation(summary = "Create a new Assessment", description = "Creates a new Assessment record")
    @ApiResponse(responseCode = "201", description = "Assessment created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<TestDto> createAssessment(@RequestBody TestDto test) {
        return ResponseEntity.ok(testService.createTest(test));
    }

    @GetMapping
    @Operation(summary = "Get all Assessments", description = "Retrieves all Assessment records")
    @ApiResponse(responseCode = "200", description = "List of Assessments retrieved successfully")
    @ApiResponse(responseCode = "404", description = "No Assessments found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<TestDto>> getAllAssessments() {
        return ResponseEntity.ok(testService.getAllTests());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Assessment by ID", description = "Retrieves a Assessment record by its ID")
    @ApiResponse(responseCode = "200", description = "Assessment retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Assessment not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<TestDto> getAssessmentById(@PathVariable UUID id) {
        return ResponseEntity.ok(testService.getTestById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Assessment", description = "Updates an existing Assessment record")
    @ApiResponse(responseCode = "200", description = "Assessment updated successfully")
    @ApiResponse(responseCode = "404", description = "Assessment not found")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<TestDto> updateAssessment(@PathVariable UUID id, @RequestBody TestDto test) {
        return ResponseEntity.ok(testService.updateTest(id, test));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Assessment", description = "Deletes a Assessment record by its ID")
    @ApiResponse(responseCode = "204", description = "Assessment deleted successfully")
    @ApiResponse(responseCode = "404", description = "Assessment not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> deleteAssessment(@PathVariable UUID id) {
        testService.deleteTest(id);
        return ResponseEntity.noContent().build();
    }
}
