//package com.junjie.userservice.accounts.service;
//
//import com.junjie.userservice.accounts.model.dto.UpdateUsernameGoToTweetMicro;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(MockitoExtension.class)
//class RabbitMQSenderTest {
//
//    @Mock
//    private RabbitTemplate rabbitTemplate;
//
//    @InjectMocks
//    private RabbitMQSender rabbitMQSender;
//
//    @Test
//    void send_ShouldCallRabbitTemplateWithCorrectArguments() {
//        // Arrange
//        UpdateUsernameGoToTweetMicro dto = new UpdateUsernameGoToTweetMicro("oldUser","newUser");
//
//        // Act
//        rabbitMQSender.send(dto);
//
//        // Assert
//        verify(rabbitTemplate).convertAndSend("user-tweet-exchange", "user.updated", dto);
//    }
//}
