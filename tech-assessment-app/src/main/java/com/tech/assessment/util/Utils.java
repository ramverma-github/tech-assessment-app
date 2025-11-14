package com.tech.assessment.util;

import com.tech.assessment.dto.QuestionDto;
import com.tech.assessment.dto.TestResultDto;
import com.tech.assessment.model.Question;
import com.tech.assessment.model.TestResult;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class Utils {

    public Set<UUID> toQuestionIds(Set<Question> questions) {
        return questions.stream()
                .map(Question::getId)
                .collect(Collectors.toSet());
    }

    public Set<UUID> toTestResultIds(Set<TestResult> results) {
        return results.stream()
                .map(TestResult::getId)
                .collect(Collectors.toSet());
    }

    public QuestionDto toQuestionDto(Question question) {
        return new QuestionDto(
                question.getId(),
                question.getDescription(),
                question.getOptionA(),
                question.getOptionB(),
                question.getOptionC(),
                question.getOptionD(),
                question.getCorrectAnswer(),
                question.getCategory(),
                question.getDifficulty()
        );
    }

    public Question toQuestion(QuestionDto questionDto) {
        return Question.builder()
                .description(questionDto.description())
                .optionA(questionDto.optionA())
                .optionB(questionDto.optionB())
                .optionC(questionDto.optionC())
                .optionD(questionDto.optionD())
                .correctAnswer(questionDto.correctAnswer())
                .category(questionDto.category())
                .difficulty(questionDto.difficulty())
                .build();
    }

    public TestResultDto toTestResultDto(TestResult testResult) {
        return new TestResultDto(
                testResult.getId(),
                testResult.getTest().getId(),
                testResult.getUser().getId(),
                testResult.getScore(),
                testResult.getCorrect(),
                testResult.getWrong(),
                testResult.getSubmittedAt()
        );
    }

}
