package com.tech.assessment.service;

import com.tech.assessment.dto.QuestionDto;
import com.tech.assessment.model.Question;
import com.tech.assessment.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<QuestionDto> getAllQuestions() {
        return questionRepository.findAll()
                .stream()
                .map(q -> new QuestionDto(
                        q.getId(),
                        q.getDescription(),
                        q.getOptionA(),
                        q.getOptionB(),
                        q.getOptionC(),
                        q.getOptionD(),
                        q.getCorrectAnswer(),
                        q.getCategory(),
                        q.getDifficulty()
                )).toList();
    }

    @Override
    public QuestionDto getQuestionById(UUID id) {
        return questionRepository.findById(id)
                .map(q -> new QuestionDto(
                        q.getId(),
                        q.getDescription(),
                        q.getOptionA(),
                        q.getOptionB(),
                        q.getOptionC(),
                        q.getOptionD(),
                        q.getCorrectAnswer(),
                        q.getCategory(),
                        q.getDifficulty()
                )).orElseThrow(() -> new RuntimeException("Question not found"));
    }

    @Override
    public QuestionDto createQuestion(QuestionDto questionDto) {
        Question question = new Question();
        question.setDescription(questionDto.description());
        question.setOptionA(questionDto.optionA());
        question.setOptionB(questionDto.optionB());
        question.setOptionC(questionDto.optionB());
        question.setOptionD(questionDto.optionB());
        question.setCorrectAnswer(questionDto.correctAnswer());
        question.setCategory(questionDto.correctAnswer());
        question.setDifficulty(questionDto.difficulty());
        Question savedQuestion = questionRepository.save(question);
        return new QuestionDto(
                savedQuestion.getId(),
                savedQuestion.getDescription(),
                savedQuestion.getOptionA(),
                savedQuestion.getOptionB(),
                savedQuestion.getOptionC(),
                savedQuestion.getOptionD(),
                savedQuestion.getCorrectAnswer(),
                savedQuestion.getCategory(),
                savedQuestion.getDifficulty()
        );
    }

    @Override
    public QuestionDto updateQuestion(UUID id, QuestionDto dto) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id " + id));

        question.setDescription(dto.description());
        question.setOptionA(dto.optionA());
        question.setOptionB(dto.optionB());
        question.setOptionC(dto.optionC());
        question.setOptionD(dto.optionD());
        question.setCorrectAnswer(dto.correctAnswer());
        question.setCategory(dto.category());
        question.setDifficulty(dto.difficulty());
        Question updatedQuestion = questionRepository.save(question);
        return new QuestionDto(
                updatedQuestion.getId(),
                updatedQuestion.getDescription(),
                updatedQuestion.getOptionA(),
                updatedQuestion.getOptionB(),
                updatedQuestion.getOptionC(),
                updatedQuestion.getOptionD(),
                updatedQuestion.getCorrectAnswer(),
                updatedQuestion.getCategory(),
                updatedQuestion.getDifficulty()
        );
    }

    @Override
    public void deleteQuestion(UUID id) {
        if (!questionRepository.existsById(id)) {
            throw new RuntimeException("Question not found with id " + id);
        }
        questionRepository.deleteById(id);
    }
}
