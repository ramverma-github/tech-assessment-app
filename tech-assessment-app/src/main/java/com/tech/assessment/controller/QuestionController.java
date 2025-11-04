package com.tech.assessment.controller;

import com.tech.assessment.model.Question;
import com.tech.assessment.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping
    @Operation(summary = "Get all Questions", description = "Retrieves all Question records")
    @ApiResponse(responseCode = "200", description = "List of Questions retrieved successfully")
    @ApiResponse(responseCode = "404", description = "No Questions found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok(questionService.getAllQuestions());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Question by ID", description = "Retrieves a Question record by its ID")
    @ApiResponse(responseCode = "200", description = "Question retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Question not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Question> getQuestionById(@PathVariable UUID id) {
        return questionService.getQuestionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new Question", description = "Creates a new Question record")
    @ApiResponse(responseCode = "201", description = "Question created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        return ResponseEntity.ok(questionService.createQuestion(question));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Question", description = "Updates an existing Question record")
    @ApiResponse(responseCode = "200", description = "Question updated successfully")
    @ApiResponse(responseCode = "404", description = "Question not found")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Question> updateQuestion(@PathVariable UUID id, @RequestBody Question question) {
        return ResponseEntity.ok(questionService.updateQuestion(id, question));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Question", description = "Deletes an existing Question record")
    @ApiResponse(responseCode = "204", description = "Question deleted successfully")
    @ApiResponse(responseCode = "404", description = "Question not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Void> deleteQuestion(@PathVariable UUID id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
