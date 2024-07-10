package io.say.better.client.chatbot.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ChatbotRequest(
    @JsonProperty("sentence")
    val sentence: String,
    @JsonProperty("user_id")
    val email: String,
)
