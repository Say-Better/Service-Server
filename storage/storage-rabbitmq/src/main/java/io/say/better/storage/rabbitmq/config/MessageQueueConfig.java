package io.say.better.storage.rabbitmq.config;


import lombok.Getter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageQueueConfig {

    @Value("${spring.rabbitmq.host}")
    private String rabbitmqHost;
    @Value("${spring.rabbitmq.port}")
    private int rabbitmqPort;
    @Value("${spring.rabbitmq.username}")
    private String rabbitmqUsername;
    @Value("${spring.rabbitmq.password}")
    private String rabbitmqPassword;
    @Value("${rabbitmq.queue.name}")
    @Getter
    private String queueName;
    @Value("${rabbitmq.exchange.name}")
    @Getter
    private String exchangeName;
    @Value("${rabbitmq.routing.key}")
    @Getter
    private String routingKey;

    /**
     * yml의 큐 이름을 이용햐 큐 생성 (Bean
     * */
    @Bean
    protected Queue queue() {
        return new Queue(queueName);
    }

    /**
     * yml의 exchange 이름으로 exchange 생성 (Bean)
     * */
    @Bean
    protected DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }

    /**
     * 큐 / 익스체인지 바인딩
     * */
    @Bean
    protected Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(routingKey);
    }

    /**
     * 커넥션 세팅
     * */
    @Bean
    protected ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitmqHost, rabbitmqPort);
        connectionFactory.setUsername(rabbitmqUsername);
        connectionFactory.setUsername(rabbitmqPassword);
        return connectionFactory;
    }

    /**
     * Jackson Json Converter 세팅
     * */
    @Bean
    protected MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * RabbitTemplate 빈 세팅
     * */
    @Bean
    protected RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter converter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter);
        return rabbitTemplate;
    }
}
