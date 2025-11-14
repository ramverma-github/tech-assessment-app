package com.tech.assessment.dto;

import java.util.UUID;

public record QuestionDto(UUID id,
        String description,
        String optionA,
        String optionB,
        String optionC,
        String optionD,
        String correctAnswer,
        String category,
        String difficulty) {}
