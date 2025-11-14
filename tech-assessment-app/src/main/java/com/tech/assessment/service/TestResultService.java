package com.tech.assessment.service;

import com.tech.assessment.dto.TestResultDto;
import com.tech.assessment.model.TestResult;

import java.util.List;
import java.util.UUID;

public interface TestResultService {

    TestResultDto createResult(TestResultDto result);
    TestResultDto getResultById(UUID id);
    List<TestResultDto> getAllResults();
    List<TestResultDto> getResultsByUserId(UUID userId);
    List<TestResultDto> getResultsByTestId(UUID testId);
    TestResultDto updateResult(UUID id, TestResultDto result);
    void deleteResult(UUID id);
}
