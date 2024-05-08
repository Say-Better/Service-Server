package io.say.better.storage.rabbitmq.util;

import io.say.better.storage.rabbitmq.config.MessageQueueConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * MessageQueue 관련 유틸
 * @author destiny3912
 * @since 04.05.24
 * */
@Component
@RequiredArgsConstructor
public class MessageQueueUtil {

    private final MessageQueueConfig config;
    private final RabbitTemplate rabbitTemplate;

    /**
     * RabbitMQ publish 메서드 </br>
     * @param message publish 할 메시지 객체
     * */
    public <T> void publish(T message) {
        rabbitTemplate.convertAndSend(config.getExchangeName(), config.getRoutingKey(), message);
    }
}
