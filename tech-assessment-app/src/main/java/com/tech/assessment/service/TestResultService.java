package com.tech.assessment.service;

import com.tech.assessment.model.TestResult;

import java.util.List;
import java.util.UUID;

public interface TestResultService {

    TestResult createResult(TestResult result);
    TestResult getResultById(UUID id);
    List<TestResult> getAllResults();
    List<TestResult> getResultsByUserId(UUID userId);
    List<TestResult> getResultsByTestId(UUID testId);
    TestResult updateResult(UUID id, TestResult result);
    void deleteResult(UUID id);
}
