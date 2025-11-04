package com.tech.assessment.service;

import com.tech.assessment.model.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TestService {
    List<Test> getAllTests();
    Optional<Test> getTestById(UUID id);
    Test createTest(Test test);
    Test updateTest(UUID id, Test updatedTest);
    void deleteTest(UUID id);
}
