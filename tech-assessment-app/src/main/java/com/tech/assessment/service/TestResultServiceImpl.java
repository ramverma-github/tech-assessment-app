package com.tech.assessment.service.impl;

import com.tech.assessment.dto.TestResultDto;
import com.tech.assessment.model.Test;
import com.tech.assessment.model.TestResult;
import com.tech.assessment.model.User;
import com.tech.assessment.repository.TestRepository;
import com.tech.assessment.repository.TestResultRepository;
import com.tech.assessment.repository.UserRepository;
import com.tech.assessment.service.TestResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TestResultServiceImpl implements TestResultService {

    private final TestResultRepository testResultRepository;
    private final TestRepository testRepository;
    private final UserRepository userRepository;

    @Override
    public TestResultDto createResult(TestResultDto dto) {
        Test test = testRepository.findById(dto.testId())
                .orElseThrow(() -> new RuntimeException("Test not found with id " + dto.testId()));

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + dto.userId()));


        TestResult entity = new TestResult();
        entity.setTest(test);
        entity.setUser(user);
        entity.setScore(dto.score());
        entity.setCorrect(dto.correct());
        entity.setWrong(dto.wrong());
        entity.setSubmittedAt(dto.submittedAt());

        TestResult saved = testResultRepository.save(entity);

        return new TestResultDto(
                saved.getId(),
                saved.getTest().getId(),
                saved.getUser().getId(),
                saved.getScore(),
                saved.getCorrect(),
                saved.getWrong(),
                saved.getSubmittedAt()
        );
    }

    @Override
    public TestResultDto getResultById(UUID id) {
        return testResultRepository.findById(id)
                .map(testResult -> new TestResultDto(
                        testResult.getId(),
                        testResult.getTest().getId(),
                        testResult.getUser().getId(),
                        testResult.getScore(),
                        testResult.getCorrect(),
                        testResult.getWrong(),
                        testResult.getSubmittedAt()))
                .orElseThrow(() -> new RuntimeException("TestResult not found with ID: " + id));
    }

    @Override
    public List<TestResultDto> getAllResults() {
        return testResultRepository.findAll().stream()
                .map(testResult -> new TestResultDto(
                        testResult.getId(),
                        testResult.getTest().getId(),
                        testResult.getUser().getId(),
                        testResult.getScore(),
                        testResult.getCorrect(),
                        testResult.getWrong(),
                        testResult.getSubmittedAt())).toList();
    }

    @Override
    public List<TestResultDto> getResultsByUserId(UUID userId) {
        return testResultRepository.findByUserId(userId).stream()
                .map(testResult -> new TestResultDto(
                        testResult.getId(),
                        testResult.getTest().getId(),
                        testResult.getUser().getId(),
                        testResult.getScore(),
                        testResult.getCorrect(),
                        testResult.getWrong(),
                        testResult.getSubmittedAt())).toList();
    }

    @Override
    public List<TestResultDto> getResultsByTestId(UUID testId) {
        return testResultRepository.findByTestId(testId).stream()
                .map(testResult -> new TestResultDto(
                        testResult.getId(),
                        testResult.getTest().getId(),
                        testResult.getUser().getId(),
                        testResult.getScore(),
                        testResult.getCorrect(),
                        testResult.getWrong(),
                        testResult.getSubmittedAt())).toList();
    }

    @Override
    public TestResultDto updateResult(UUID id, TestResultDto dto) {
        TestResult result = testResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TestResult not found with id " + id));

        if (!result.getTest().getId().equals(dto.testId())) {
            Test test = testRepository.findById(dto.testId())
                    .orElseThrow(() -> new RuntimeException("Test not found with id " + dto.testId()));
            result.setTest(test);
        }

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new RuntimeException("Test not found with id " + dto.userId()));

        result.setUser(user);
        result.setScore(dto.score());
        result.setCorrect(dto.correct());
        result.setWrong(dto.wrong());
        result.setSubmittedAt(dto.submittedAt());

        TestResult saved = testResultRepository.save(result);

        return new TestResultDto(
                saved.getId(),
                saved.getTest().getId(),
                saved.getUser().getId(),
                saved.getScore(),
                saved.getCorrect(),
                saved.getWrong(),
                saved.getSubmittedAt()
        );
    }

    @Override
    public void deleteResult(UUID id) {
        if (!testResultRepository.existsById(id)) {
            throw new RuntimeException("TestResult not found with ID: " + id);
        }
        testResultRepository.deleteById(id);
    }
}
