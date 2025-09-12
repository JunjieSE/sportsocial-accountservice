//package com.junjie.SportPost.accounts.service;
//
//import com.junjie.SportPost.accounts.model.dto.UpdateUsernameGoToTweetMicro;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class RabbitMQSender {
//    private final RabbitTemplate rabbitTemplate;
//
//    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
//        this.rabbitTemplate = rabbitTemplate;
//    }
//
//    public void send(UpdateUsernameGoToTweetMicro dto) {
//        rabbitTemplate.convertAndSend("user-tweet-exchange", "user.updated", dto);
//    }
//
//}
