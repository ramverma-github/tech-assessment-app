package com.tech.assessment.service;

import com.tech.assessment.dto.TestDto;

import java.util.List;
import java.util.UUID;

public interface TestService {
    List<TestDto> getAllTests();
    TestDto getTestById(UUID id);
    TestDto createTest(TestDto testDto);
    TestDto updateTest(UUID id, TestDto testDto);
    void deleteTest(UUID id);
}
