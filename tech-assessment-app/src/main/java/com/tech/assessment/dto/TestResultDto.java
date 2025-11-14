package com.tech.assessment.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TestResultDto(
        UUID id,
        UUID testId,
        UUID userId,
        int score,
        int correct,
        int wrong,
        LocalDateTime submittedAt) {}
