package com.tech.assessment.service;

import com.tech.assessment.model.Test;
import com.tech.assessment.repository.TestRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    public TestServiceImpl(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Override
    public List<Test> getAllTests() {
        return testRepository.findAll();
    }

    @Override
    public Optional<Test> getTestById(UUID id) {
        return testRepository.findById(id);
    }

    @Override
    public Test createTest(Test test) {
        return testRepository.save(test);
    }

    @Override
    public Test updateTest(UUID id, Test updatedTest) {
        return testRepository.findById(id)
                .map(existingTest -> {
                    existingTest.setTitle(updatedTest.getTitle());
                    existingTest.setDurationMinutes(updatedTest.getDurationMinutes());
                    existingTest.setCategory(updatedTest.getCategory());
                    existingTest.setStartTime(updatedTest.getStartTime());
                    existingTest.setEndTime(updatedTest.getEndTime());
                    return testRepository.save(existingTest);
                })
                .orElseThrow(() -> new RuntimeException("Test not found with ID: " + id));
    }

    @Override
    public void deleteTest(UUID id) {
        testRepository.deleteById(id);
    }
}
