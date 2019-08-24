package com.company.retailservice.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PointsProducer {
    public static final String QUEUE_NAME="level-up-queue";
    public static final String ROUTING_KEY="level.#";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public PointsProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
}
