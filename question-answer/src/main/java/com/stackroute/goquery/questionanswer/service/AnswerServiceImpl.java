package com.stackroute.goquery.questionanswer.service;


import com.stackroute.goquery.questionanswer.dao.QuestionRepository;
import com.stackroute.goquery.questionanswer.domain.Answer;
import com.stackroute.goquery.questionanswer.domain.Comment;
import com.stackroute.goquery.questionanswer.domain.Question;
import org.apache.commons.collections.ArrayStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.CachingResourceTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AnswerServiceImpl implements AnswerService {

    QuestionRepository questionRepository;
    Answer answer;
    Question question;

    @Autowired
    public AnswerServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Autowired
    MongoTemplate mongoTemplate;

    private static final Logger logger = LoggerFactory.getLogger(AnswerServiceImpl.class);


    @Override
    public Answer addNewAnswer(Answer answer, UUID questionId) {
        Question question = null;
        ArrayList<Answer> answers = new ArrayList<>();
        Query query = new Query(Criteria.where("uuidQuestionId").is(questionId));
        List<Question> questionList = mongoTemplate.find(query, Question.class);
        if (questionList.size() == 0) {
            logger.error("Question with id:" + questionId + " doesn't exist");
        } else {
            question = questionList.get(0);
            answer.setIntAnswerId(UUID.randomUUID());
            answer.setAccepted(false);
            if (question.getAnswers() == null) {
                answers.add(answer);
            } else {
                answers = question.getAnswers();
                answers.add(answer);
            }
            Query query2 = new Query(Criteria.where("uuidQuestionId").is(questionId));
            Update updateQuery = new Update();
            updateQuery.set("answers", answers);
            mongoTemplate.upsert(query, updateQuery, "Question");
        }
        return answer;
    }

    @Override
    public List<Answer> getAllAnswers(UUID questionId) {
        ArrayList<Answer> answers = new ArrayList<>();
        Query query = new Query(Criteria.where("uuidQuestionId").is(questionId));
        List<Question> questionList = mongoTemplate.find(query, Question.class);
        if (questionList.size() == 0) {
            logger.info("Question with id:" + questionId + " doesn't exist");
        } else {
            answers = questionList.get(0).getAnswers();
        }
        return answers;
    }

    @Override
    public void updateAnswer(Answer answer, UUID questionId) {
        Question question = questionRepository.findById(questionId).get();
        int idx = 0;
        for (Answer answer1 : question.getAnswers()) {
            if (answer1.getIntAnswerId() == answer.getIntAnswerId()) {
                idx = question.getAnswers().indexOf(answer1);
            }
        }
        question.getAnswers().remove(idx);
        question.getAnswers().add(idx, answer);
        questionRepository.deleteById(questionId);
        questionRepository.save(question);
    }

    @Override
    public int upvoteAnswer(UUID answerId) {
        Query query = new Query(Criteria.where("answers.intAnswerId").is(answerId));
        int vote = 0;
        List<Question> questionList = mongoTemplate.find(query, Question.class);
        if (questionList.size() == 0) {
            logger.info("answer with answer id:" + answerId + " doesn't exist");
            return vote;
        } else {
            List<Answer> answerList = questionList.get(0).getAnswers();
            logger.info("AnswerList:" + answerList);
            if (answerList.size() == 0) {
                logger.info("answer with answer id:" + answerId + " doesn't exist");
                return vote;
            } else {
                for (Answer answer : answerList) {
                    if (answer.getIntAnswerId().equals(answerId)) {
                        vote = answer.getIntAnswerUpvote() + 1;
                        answer.setIntAnswerUpvote(vote);
                    }
                }
                Update updateQuery = new Update();
                updateQuery.set("answers", answerList);
                mongoTemplate.upsert(query, updateQuery, Question.class);
                return vote;
            }
        }
    }

    @Override
    public int downvoteAnswer(UUID answerId) {
        Query query = new Query(Criteria.where("answers.intAnswerId").is(answerId));
        int vote = 0;
        List<Question> questionList = mongoTemplate.find(query, Question.class);
        if (questionList.size() == 0) {
            logger.info("answer with answer id:" + answerId + " doesn't exist");
            return vote;
        } else {
            List<Answer> answerList = questionList.get(0).getAnswers();
            logger.info("AnswerList:" + answerList);
            if (answerList.size() == 0) {
                logger.info("answer with answer id:" + answerId + " doesn't exist");
                return vote;
            } else {
                for (Answer answer : answerList) {
                    if (answer.getIntAnswerId().equals(answerId)) {
                        vote = answer.getIntAnswerDownvote() - 1;
                        answer.setIntAnswerDownvote(vote);
                    }
                }
                Update updateQuery = new Update();
                updateQuery.set("answers", answerList);
                mongoTemplate.upsert(query, updateQuery, Question.class);
                return vote;
            }
        }
    }

    @Override
    public boolean acceptAnswer(UUID answerId) {
        Query query = new Query(Criteria.where("answers.intAnswerId").is(answerId));
        List<Question> questionList = mongoTemplate.find(query, Question.class);
        if (questionList.size() == 0) {
            logger.info("answer with answer id:" + answerId + " doesn't exist");
            return false;
        } else {
            List<Answer> answerList = questionList.get(0).getAnswers();
            logger.info("AnswerList:" + answerList);
            if (answerList.size() == 0) {
                logger.info("answer with answer id:" + answerId + " doesn't exist");
                return false;
            } else {
                for (Answer answer : answerList) {
                    if (answer.getIntAnswerId().equals(answerId)) {
                        answer.setAccepted(true);
                    }
                }
                Update updateQuery = new Update();
                updateQuery.set("answers", answerList);
                updateQuery.set("acceptedAnswer", true);
                mongoTemplate.upsert(query, updateQuery, Question.class);
                return true;
            }
        }
    }

    @Override
    public boolean commentAnswer(UUID answerId, Comment commentGiven) {
        Query query = new Query(Criteria.where("answers.intAnswerId").is(answerId));
        List<Comment> comments = new ArrayList<Comment>();
        List<Question> questionList = mongoTemplate.find(query, Question.class);
        if (questionList.size() == 0) {
            logger.info("answer with answer id:" + answerId + " doesn't exist");
            return false;
        } else {
            List<Answer> answerList = questionList.get(0).getAnswers();
            logger.info("AnswerList:" + answerList);
            if (answerList.size() == 0) {
                logger.info("answer with answer id:" + answerId + " doesn't exist");
                return false;
            } else {
                for (Answer answer : answerList) {
                    if (answer.getIntAnswerId().equals(answerId)) {
                        if (answer.getComments() == null) {
                            comments.add(commentGiven);
                        } else {
                            comments = answer.getComments();
                            comments.add(commentGiven);
                        }
                        answer.setComments(comments);
                    }
                }
                Update updateQuery = new Update();
                updateQuery.set("answers", answerList);
                mongoTemplate.upsert(query, updateQuery, Question.class);
                return true;
            }
        }
    }

    @Override
    public List<Comment> showAllComments(UUID answerId) {
        Query query = new Query(Criteria.where("answers.intAnswerId").is(answerId));
        List<Comment> comments = new ArrayList<Comment>();
        List<Question> questionList = mongoTemplate.find(query, Question.class);
        if (questionList.size() == 0) {
            logger.info("answer with answer id:" + answerId + " doesn't exist");
            return null;
        } else {
            List<Answer> answerList = questionList.get(0).getAnswers();
            logger.info("AnswerList:" + answerList);
            if (answerList.size() == 0) {
                logger.info("answer with answer id:" + answerId + " doesn't exist");
                return null;
            } else {
                for (Answer answer : answerList) {
                    if (answer.getIntAnswerId().equals(answerId)) {
                        comments = answer.getComments();
                    }
                }
            }
            return comments;
        }}
    public List<Answer> getAnswerByMail(String mail) {
        List<Answer> answers = new ArrayList<>();
        Query query = new Query(Criteria.where("answers.strAnswererEmail").is(mail));
        List<Question> questionList = mongoTemplate.find(query, Question.class);
        for(Question questionElement:questionList) {
            List<Answer> existingAnswer = questionElement.getAnswers();
            for(Answer answerElement:existingAnswer) {
                if (answerElement.getStrAnswererEmail().equals(mail)){
                    answers.add(answerElement);
                }
            }
        }
        return answers;
    }
}


