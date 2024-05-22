package io.say.better.storage.rabbitmq.config

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MessageQueueConfig @Autowired constructor(
        @Value("\${spring.rabbitmq.host}")
        private val rabbitmqHost: String,

        @Value("\${spring.rabbitmq.port}")
        private val rabbitmqPort: Int = 0,

        @Value("\${spring.rabbitmq.username}")
        private val rabbitmqUsername: String,

        @Value("\${spring.rabbitmq.password}")
        private val rabbitmqPassword: String,

        @Value("\${rabbitmq.queue.name}")
        private val queueName: String,

        @Value("\${rabbitmq.exchange.name}")
        private val exchangeName: String,

        @Value("\${rabbitmq.routing.key}")
        private val routingKey: String

) {
    fun getExchangeName(): String {
        return exchangeName
    }

    fun getRoutingKey(): String {
        return routingKey
    }

    /**
     * yml의 큐 이름을 이용해 큐 생성 (Bean)
     */
    @Bean
    protected fun queue(): Queue {
        return Queue(queueName)
    }

    /**
     * yml의 exchange 이름으로 exchange 생성 (Bean)
     */
    @Bean
    protected fun exchange(): DirectExchange {
        return DirectExchange(exchangeName)
    }

    /**
     * 큐 / 익스체인지 바인딩
     */
    @Bean
    protected fun binding(queue: Queue?, exchange: DirectExchange?): Binding {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(routingKey)
    }

    /**
     * 커넥션 세팅
     */
    @Bean
    protected fun connectionFactory(): ConnectionFactory {
        val connectionFactory: CachingConnectionFactory = CachingConnectionFactory(rabbitmqHost, rabbitmqPort)
        connectionFactory.username = rabbitmqUsername
        connectionFactory.username = rabbitmqPassword
        return connectionFactory
    }

    /**
     * Jackson Json Converter 세팅
     */
    @Bean
    protected fun converter(): MessageConverter {
        return Jackson2JsonMessageConverter()
    }

    /**
     * RabbitTemplate 빈 세팅
     */
    @Bean
    protected fun rabbitTemplate(connectionFactory: ConnectionFactory, converter: MessageConverter): RabbitTemplate {
        val rabbitTemplate: RabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = converter
        return rabbitTemplate
    }
}
