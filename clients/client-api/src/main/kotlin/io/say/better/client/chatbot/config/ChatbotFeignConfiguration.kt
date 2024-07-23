package io.say.better.client.chatbot.config

import feign.RequestInterceptor
import org.springframework.context.annotation.Bean

class ChatbotFeignConfiguration {
    @Bean
    fun requestInterceptor(): RequestInterceptor =
        RequestInterceptor {
            it.header("Content-Type", "application/json; charset=UTF-8")
        }
}
