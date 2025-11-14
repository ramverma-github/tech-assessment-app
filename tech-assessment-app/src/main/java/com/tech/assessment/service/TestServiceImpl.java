package com.tech.assessment.service;

import com.tech.assessment.dto.TestDto;
import com.tech.assessment.model.Question;
import com.tech.assessment.model.Test;
import com.tech.assessment.model.TestResult;
import com.tech.assessment.repository.QuestionRepository;
import com.tech.assessment.repository.TestRepository;
import com.tech.assessment.repository.TestResultRepository;
import com.tech.assessment.util.Utils;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    private final QuestionRepository questionRepository;

    private final TestResultRepository testResultRepository;

    private final Utils utils;

    @Override
    @Transactional(readOnly = true)
    public List<TestDto> getAllTests() {
        List<Test> testList = testRepository.findAllWithChildren();
        return testList.stream()
                .map(test -> new TestDto(
                        test.getId(),
                        test.getTitle(),
                        test.getDurationMinutes(),
                        test.getCategory(),
                        test.getStartTime(),
                        test.getEndTime(),
                        utils.toQuestionIds(test.getQuestions()),
                        utils.toTestResultIds(test.getResults())))
                .toList();
    }

    @Override
    public TestDto getTestById(UUID id) {
        return testRepository.findById(id)
                .map(test -> new TestDto(
                        test.getId(),
                        test.getTitle(),
                        test.getDurationMinutes(),
                        test.getCategory(),
                        test.getStartTime(),
                        test.getEndTime(),
                        utils.toQuestionIds(test.getQuestions()),
                        utils.toTestResultIds(test.getResults())))
                .orElseThrow(() -> new RuntimeException("Test not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public TestDto createTest(TestDto testDto) {
        Test test = new Test();
        test.setTitle(testDto.title());
        test.setDurationMinutes(testDto.durationMinutes());
        test.setCategory(testDto.category());
        test.setStartTime(testDto.startTime());
        test.setEndTime(testDto.endTime());

        Set<Question> questions = new HashSet<>();
        for (UUID qId : testDto.questionIds()) {
            Optional<Question> question = questionRepository.findById(qId);
            question.ifPresent(questions::add);
        }

        test.setQuestions(questions);
        Test savedTest = testRepository.save(test);
        return new TestDto(
                savedTest.getId(),
                savedTest.getTitle(),
                savedTest.getDurationMinutes(),
                savedTest.getCategory(),
                savedTest.getStartTime(),
                savedTest.getEndTime(),
                utils.toQuestionIds(savedTest.getQuestions()),
                utils.toTestResultIds(savedTest.getResults())
        );
    }

    @Override
    @Transactional
    public TestDto updateTest(UUID testId, TestDto dto) {

        Test test = testRepository.findByIdWithChildren(testId)
                .orElseThrow(() -> new RuntimeException("Test not found"));

        test.setTitle(dto.title());
        test.setDurationMinutes(dto.durationMinutes());
        test.setCategory(dto.category());
        test.setStartTime(dto.startTime());
        test.setEndTime(dto.endTime());

        Set<Question> questions = dto.questionIds().stream()
                .map(qId -> questionRepository.findById(qId)
                        .orElseThrow(() -> new RuntimeException("Question not found: " + qId)))
                .collect(Collectors.toSet());

/*
        Set<TestResult> testResults = dto.resultIds().stream()
                .map(id -> testResultRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Result not found: " + id)))
                .collect(Collectors.toSet());
*/

        test.setQuestions(new HashSet<>(questions));
  //      test.setResults(new HashSet<>(testResults));

        Test updated = testRepository.save(test);

        return new TestDto(
                updated.getId(),
                updated.getTitle(),
                updated.getDurationMinutes(),
                updated.getCategory(),
                updated.getStartTime(),
                updated.getEndTime(),
                utils.toQuestionIds(updated.getQuestions()),
                utils.toTestResultIds(updated.getResults())
        );
    }

    @Override
    public void deleteTest(UUID id) {
        testRepository.deleteById(id);
    }
}
