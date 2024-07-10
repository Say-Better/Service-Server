package io.say.better.client.chatbot.client

import io.say.better.client.chatbot.api.ChatbotApi
import io.say.better.client.chatbot.dto.ChatbotRequest
import io.say.better.client.chatbot.dto.ChatbotResponse
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@PropertySource("classpath:client-api.yml")
class ChatbotClient internal constructor(
    private val chatbotApi: ChatbotApi,
) {
    fun chat(request: ChatbotRequest): ChatbotResponse.Response = chatbotApi.chat(request)
}
