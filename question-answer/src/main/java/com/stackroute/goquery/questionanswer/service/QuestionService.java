package com.stackroute.goquery.questionanswer.service;

import com.stackroute.goquery.questionanswer.domain.Question;
import com.stackroute.goquery.questionanswer.exception.QuestionAlreadyExists;

import java.util.List;
import java.util.UUID;

public interface QuestionService {
    Question addNewQuestion(Question question) throws QuestionAlreadyExists;

    List<Question> getAllquestions();

    List<Question> getQuestionByEmail(String email);

    void updateQuestion(Question question);

    Question getQuestionById(UUID id);

    void deleteQuestionById(UUID id);
}

