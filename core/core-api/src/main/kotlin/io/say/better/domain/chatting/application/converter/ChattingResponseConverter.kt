package io.say.better.domain.chatting.application.converter

import io.say.better.client.chatbot.dto.ChatbotResponse
import io.say.better.domain.chatting.ui.dto.ChattingResponse

class ChattingResponseConverter private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun toChattingResponse(chatbotResponse: ChatbotResponse.Response): ChattingResponse =
            ChattingResponse(
                chatbotResponse.answer1,
                chatbotResponse.answer2,
            )
    }
}
