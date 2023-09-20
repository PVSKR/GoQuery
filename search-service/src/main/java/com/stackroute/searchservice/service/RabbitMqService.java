package com.stackroute.searchservice.service;

import com.stackroute.searchservice.domain.Question;
import com.stackroute.searchservice.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqService implements RabbitListenerConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqService.class);
    private QuestionRepository questionRepository;

    @Autowired
    public RabbitMqService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void recievedQuestion(Question question) {
        logger.info("received question:"+question.getStrQuestionTitle());
        questionRepository.insertQuestion(question);
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {

    }
}