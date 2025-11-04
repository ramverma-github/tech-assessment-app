package com.tech.assessment.service.impl;

import com.tech.assessment.model.TestResult;
import com.tech.assessment.repository.TestResultRepository;
import com.tech.assessment.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TestResultServiceImpl implements TestResultService {

    private final TestResultRepository testResultRepository;

    @Autowired
    public TestResultServiceImpl(TestResultRepository testResultRepository) {
        this.testResultRepository = testResultRepository;
    }

    @Override
    public TestResult createResult(TestResult result) {
        return testResultRepository.save(result);
    }

    @Override
    public TestResult getResultById(UUID id) {
        return testResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TestResult not found with ID: " + id));
    }

    @Override
    public List<TestResult> getAllResults() {
        return testResultRepository.findAll();
    }

    @Override
    public List<TestResult> getResultsByUserId(UUID userId) {
        return testResultRepository.findByUserId(userId);
    }

    @Override
    public List<TestResult> getResultsByTestId(UUID testId) {
        return testResultRepository.findByTestId(testId);
    }

    @Override
    public TestResult updateResult(UUID id, TestResult updatedResult) {
        TestResult existing = getResultById(id);

        existing.setScore(updatedResult.getScore());
        existing.setCorrect(updatedResult.getCorrect());
        existing.setWrong(updatedResult.getWrong());
        existing.setSubmittedAt(updatedResult.getSubmittedAt());
        existing.setTest(updatedResult.getTest());
        existing.setUserId(updatedResult.getUserId());

        return testResultRepository.save(existing);
    }

    @Override
    public void deleteResult(UUID id) {
        if (!testResultRepository.existsById(id)) {
            throw new RuntimeException("TestResult not found with ID: " + id);
        }
        testResultRepository.deleteById(id);
    }
}
