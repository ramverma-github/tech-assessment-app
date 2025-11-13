package com.tech.assessment.controller;

import com.tech.assessment.model.Test;
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
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping
    @Operation(summary = "Get all Tests", description = "Retrieves all Test records")
    @ApiResponse(responseCode = "200", description = "List of Tests retrieved successfully")
    @ApiResponse(responseCode = "404", description = "No Tests found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<Test>> getAllTests() {
        return ResponseEntity.ok(testService.getAllTests());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Test by ID", description = "Retrieves a Test record by its ID")
    @ApiResponse(responseCode = "200", description = "Test retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Test not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Test> getTestById(@PathVariable UUID id) {
        return testService.getTestById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new Test", description = "Creates a new Test record")
    @ApiResponse(responseCode = "201", description = "Test created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Test> createTest(@RequestBody Test test) {
        return ResponseEntity.ok(testService.createTest(test));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Test", description = "Updates an existing Test record")
    @ApiResponse(responseCode = "200", description = "Test updated successfully")
    @ApiResponse(responseCode = "404", description = "Test not found")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Test> updateTest(@PathVariable UUID id, @RequestBody Test test) {
        return ResponseEntity.ok(testService.updateTest(id, test));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Test", description = "Deletes a Test record by its ID")
    @ApiResponse(responseCode = "204", description = "Test deleted successfully")
    @ApiResponse(responseCode = "404", description = "Test not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> deleteTest(@PathVariable UUID id) {
        testService.deleteTest(id);
        return ResponseEntity.noContent().build();
    }
}
