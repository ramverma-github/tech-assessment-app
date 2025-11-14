package com.tech.assessment.controller;

import com.tech.assessment.dto.TestResultDto;
import com.tech.assessment.service.TestResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    private final TestResultService testResultService;

    @Autowired
    public ResultController(TestResultService testResultService) {
        this.testResultService = testResultService;
    }

    @PostMapping
    @Operation(summary = "Create a new AssessmentResult", description = "Creates a new AssessmentResult record")
    @ApiResponse(responseCode = "201", description = "AssessmentResult created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "404", description = "AssessmentResult not found")
    public ResponseEntity<TestResultDto> createAssessmentResult(@RequestBody TestResultDto result) {
        return ResponseEntity.ok(testResultService.createResult(result));
    }

    @GetMapping
    @Operation(summary = "Get all AssessmentResults", description = "Retrieves all AssessmentResults records")
    @ApiResponse(responseCode = "200", description = "List of AssessmentResults retrieved successfully")
    @ApiResponse(responseCode = "404", description = "No AssessmentResults found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<TestResultDto>> getAllAssessmentResult() {
        return ResponseEntity.ok(testResultService.getAllResults());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get AssessmentResult by ID", description = "Retrieves a AssessmentResult by its ID")
    @ApiResponse(responseCode = "200", description = "AssessmentResult retrieved successfully")
    @ApiResponse(responseCode = "404", description = "AssessmentResult not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<TestResultDto> getResultById(@PathVariable UUID id) {
        return ResponseEntity.ok(testResultService.getResultById(id));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get AssessmentResults by User ID", description = "Retrieves all AssessmentResults for a specific user")
    @ApiResponse(responseCode = "200", description = "List of AssessmentResults retrieved successfully")
    @ApiResponse(responseCode = "404", description = "No AssessmentResults found for the user")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<TestResultDto>> getResultByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(testResultService.getResultsByUserId(userId));
    }

    @GetMapping("/test/{testId}")
    @Operation(summary = "Get AssessmentResults by Test ID", description = "Retrieves all AssessmentResults for a specific test")
    @ApiResponse(responseCode = "200", description = "List of AssessmentResults retrieved successfully")
    @ApiResponse(responseCode = "404", description = "No AssessmentResults found for the test")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<TestResultDto>> getResultsByTestId(@PathVariable UUID testId) {
        return ResponseEntity.ok(testResultService.getResultsByTestId(testId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update AssessmentResult", description = "Updates an existing AssessmentResult")
    @ApiResponse(responseCode = "200", description = "AssessmentResult updated successfully")
    @ApiResponse(responseCode = "404", description = "AssessmentResult not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<TestResultDto> updateResult(@PathVariable UUID id, @RequestBody TestResultDto result) {
        return ResponseEntity.ok(testResultService.updateResult(id, result));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete AssessmentResult", description = "Deletes a AssessmentResult by its ID")
    @ApiResponse(responseCode = "200", description = "AssessmentResult deleted successfully")
    @ApiResponse(responseCode = "404", description = "AssessmentResult not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<String> deleteResult(@PathVariable UUID id) {
        testResultService.deleteResult(id);
        return ResponseEntity.ok("TestResult deleted successfully.");
    }
}
