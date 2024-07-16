package io.say.better.client.chatbot.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class ChatbotResponse private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    data class Response(
        val answer1: String = "",
        val answer2: String = "",
        val score: Int = 0,
    )
}
