package com.stackroute.goquery.questionanswer.service;

import com.stackroute.goquery.questionanswer.domain.Answer;
import com.stackroute.goquery.questionanswer.domain.Comment;
import com.stackroute.goquery.questionanswer.domain.Question;

import java.util.List;
import java.util.UUID;

public interface AnswerService {
    Answer addNewAnswer(Answer answer, UUID questionId);

    List<Answer> getAllAnswers(UUID questionId);

    void updateAnswer(Answer answer, UUID questionId);

    int upvoteAnswer(UUID answerId);

    int downvoteAnswer(UUID answerId);

    boolean acceptAnswer(UUID answerId);

    boolean commentAnswer(UUID answerId, Comment commentGiven);

    List<Comment> showAllComments(UUID answerId);
    List<Answer> getAnswerByMail(String mail);
}
