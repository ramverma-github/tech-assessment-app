package com.tech.assessment.service;

import com.tech.assessment.model.Question;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionService {
    List<Question> getAllQuestions();
    Optional<Question> getQuestionById(UUID id);
    Question createQuestion(Question question);
    Question updateQuestion(UUID id, Question question);
    void deleteQuestion(UUID id);
}
