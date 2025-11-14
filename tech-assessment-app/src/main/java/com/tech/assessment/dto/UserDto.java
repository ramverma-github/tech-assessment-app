package com.tech.assessment.dto;

import com.tech.assessment.constant.RoleType;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record UserDto(UUID id,
                      String username,
                      String password,
                      String email,
                      Set<RoleType> roles,
                      LocalDateTime createdAt,
                      LocalDateTime updatedAt) {
}
