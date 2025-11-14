package com.tech.assessment.service;

import com.tech.assessment.dto.QuestionDto;
import com.tech.assessment.model.Question;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionService {
    List<QuestionDto> getAllQuestions();
    QuestionDto getQuestionById(UUID id);
    QuestionDto createQuestion(QuestionDto question);
    QuestionDto updateQuestion(UUID id, QuestionDto question);
    void deleteQuestion(UUID id);
}
