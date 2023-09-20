package com.stackroute.goquery.userservice.service;

import com.stackroute.goquery.userservice.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {
    private RabbitTemplate rabbitTemplate;

    private static Logger logger= LoggerFactory.getLogger(RabbitMQSender.class);

    @Autowired
    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingkey;

    public void sendUser(User loginDetails) {
        logger.info("user details to be sent:"+loginDetails.getStrUserEmail());
        rabbitTemplate.convertAndSend(exchange, routingkey, loginDetails);
    }
}