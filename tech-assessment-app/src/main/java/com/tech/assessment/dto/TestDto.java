package com.tech.assessment.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public record TestDto( UUID id,
        String title,
        int durationMinutes,
        String category,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Set<UUID> questionIds,
        Set<UUID> resultIds
        ) {}
