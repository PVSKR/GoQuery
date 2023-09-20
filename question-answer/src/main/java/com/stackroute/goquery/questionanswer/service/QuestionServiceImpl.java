package com.stackroute.goquery.questionanswer.service;

import com.stackroute.goquery.questionanswer.dao.QuestionRepository;
import com.stackroute.goquery.questionanswer.domain.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class QuestionServiceImpl implements QuestionService{

    QuestionRepository questionRepository;
    private RabbitMqSender rabbitMqSender;

    private static final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);
    
    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, RabbitMqSender rabbitMqSender) {
        this.questionRepository = questionRepository;
        this.rabbitMqSender=rabbitMqSender;
    }

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Question addNewQuestion(Question question) {
        question.setUuidQuestionId(UUID.randomUUID());
        question.setAcceptedAnswer(false);
        rabbitMqSender.sendQuestion(question);
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAllquestions() {
        return questionRepository.findAll();
    }

    @Override
    public List<Question> getQuestionByEmail(String email) {
        return questionRepository.findByStrQuestionerEmail(email);
    }

    @Override
    public void updateQuestion(Question question) {
        questionRepository.deleteById(question.getUuidQuestionId());
        questionRepository.save(question);
    }

    @Override
    public Question getQuestionById(UUID id) {
        Question question = null;
        Query query = new Query(Criteria.where("uuidQuestionId").is(id));
        List<Question> questions= mongoTemplate.find(query, Question.class);
        if (questions.size() == 0) {
            logger.error("Question with id:"+ id+" not present!!");
        } else {
            logger.info("question count:"+questions.size());
            question = questions.get(0);
        }
        return question;
    }

    @Override
    public void deleteQuestionById(UUID id) {
        questionRepository.deleteById(id);
    }

}

