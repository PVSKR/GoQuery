package com.stackroute.service;

import com.stackroute.domain.Authentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQService implements RabbitListenerConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQService.class);

    private AuthenticationService authenticationService;
    @Autowired
    public RabbitMQService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void recievedMessage(Authentication userDetails) {
        logger.info("user details received:"+ userDetails.getStrUserEmail());
        authenticationService.saveUser(userDetails);
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
    }
}
