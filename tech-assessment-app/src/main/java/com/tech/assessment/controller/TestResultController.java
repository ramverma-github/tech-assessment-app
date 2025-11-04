package com.tech.assessment.controller;

import com.tech.assessment.model.TestResult;
import com.tech.assessment.service.TestResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/test-results")
public class TestResultController {

    private final TestResultService testResultService;

    @Autowired
    public TestResultController(TestResultService testResultService) {
        this.testResultService = testResultService;
    }

    @PostMapping
    @Operation(summary = "Create a new TestResult", description = "Creates a new TestResult record")
    @ApiResponse(responseCode = "201", description = "TestResult created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "404", description = "TestResult not found")
    public ResponseEntity<TestResult> createResult(@RequestBody TestResult result) {
        return ResponseEntity.ok(testResultService.createResult(result));
    }

    @GetMapping
    @Operation(summary = "Get all TestResults", description = "Retrieves all TestResult records")
    @ApiResponse(responseCode = "200", description = "List of TestResults retrieved successfully")
    @ApiResponse(responseCode = "404", description = "No TestResults found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<TestResult>> getAllResults() {
        return ResponseEntity.ok(testResultService.getAllResults());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get TestResult by ID", description = "Retrieves a TestResult by its ID")
    @ApiResponse(responseCode = "200", description = "TestResult retrieved successfully")
    @ApiResponse(responseCode = "404", description = "TestResult not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<TestResult> getResultById(@PathVariable UUID id) {
        return ResponseEntity.ok(testResultService.getResultById(id));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get TestResults by User ID", description = "Retrieves all TestResults for a specific user")
    @ApiResponse(responseCode = "200", description = "List of TestResults retrieved successfully")
    @ApiResponse(responseCode = "404", description = "No TestResults found for the user")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<TestResult>> getResultsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(testResultService.getResultsByUserId(userId));
    }

    @GetMapping("/test/{testId}")
    @Operation(summary = "Get TestResults by Test ID", description = "Retrieves all TestResults for a specific test")
    @ApiResponse(responseCode = "200", description = "List of TestResults retrieved successfully")
    @ApiResponse(responseCode = "404", description = "No TestResults found for the test")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<TestResult>> getResultsByTestId(@PathVariable UUID testId) {
        return ResponseEntity.ok(testResultService.getResultsByTestId(testId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update TestResult", description = "Updates an existing TestResult")
    @ApiResponse(responseCode = "200", description = "TestResult updated successfully")
    @ApiResponse(responseCode = "404", description = "TestResult not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<TestResult> updateResult(@PathVariable UUID id, @RequestBody TestResult result) {
        return ResponseEntity.ok(testResultService.updateResult(id, result));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete TestResult", description = "Deletes a TestResult by its ID")
    @ApiResponse(responseCode = "200", description = "TestResult deleted successfully")
    @ApiResponse(responseCode = "404", description = "TestResult not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<String> deleteResult(@PathVariable UUID id) {
        testResultService.deleteResult(id);
        return ResponseEntity.ok("TestResult deleted successfully.");
    }
}
