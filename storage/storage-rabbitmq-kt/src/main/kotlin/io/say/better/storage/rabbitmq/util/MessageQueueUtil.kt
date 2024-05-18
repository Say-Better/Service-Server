package io.say.better.storage.rabbitmq.util

import io.say.better.storage.rabbitmq.config.MessageQueueConfig
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

/**
 * MessageQueue 관련 유틸
 * @author destiny3912
 * @since 04.05.24
 */
@Component
class MessageQueueUtil(
        private val config: MessageQueueConfig,
        private val rabbitTemplate: RabbitTemplate
) {
    /**
     * RabbitMQ publish 메서드
     * @param message publish 할 메시지 객체
     */
    fun publish(message: Any) {
        rabbitTemplate.convertAndSend(config.getExchangeName(), config.getRoutingKey(), message)
    }
}
