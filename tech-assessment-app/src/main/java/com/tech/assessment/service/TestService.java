package com.tech.assessment.service;

import com.tech.assessment.dto.TestDto;
import com.tech.assessment.model.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TestService {
    List<TestDto> getAllTests();
    TestDto getTestById(UUID id);
    TestDto createTest(TestDto testDto);
    TestDto updateTest(UUID id, TestDto testDto);
    void deleteTest(UUID id);
}
