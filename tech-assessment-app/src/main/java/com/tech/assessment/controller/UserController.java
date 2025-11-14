package com.tech.assessment.controller;

import com.tech.assessment.dto.UserDto;
import com.tech.assessment.model.User;
import com.tech.assessment.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}/profile")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> userProfile(@PathVariable UUID id) {
        UserDto user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping("/register")
    @Operation(summary = "Register a user", description = "Registers a new user")
    @ApiResponse(responseCode = "201", description = "User registered successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    public ResponseEntity<?> register(@Valid @RequestBody UserDto user) {
        UserDto registeredUser = userService.registerUser(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Updates an existing user")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<?> updateUser(@PathVariable UUID id, @Valid @RequestBody UserDto user) {
        UserDto updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially update user", description = "Partially updates an existing user")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<?> updateUserPartially(@PathVariable UUID id, @RequestBody UserDto user) {
        UserDto existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        UserDto userDto = new UserDto(null,
                user.username() != null ? user.username() : existingUser.username(),
                user.password() != null ? user.password() : existingUser.password(),
                user.email() != null ? user.email() : existingUser.email(),
                user.roles() != null ? user.roles() : existingUser.roles(),
                existingUser.createdAt(),
                LocalDateTime.now()
        );
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Returns a list of all registered users")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    @ApiResponse(responseCode = "204", description = "No users found")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Returns a user by their ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved user")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID id) {
        UserDto user = userService.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Deletes a user by their ID")
    @ApiResponse(responseCode = "200", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        UserDto user = userService.getUserById(id);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

}