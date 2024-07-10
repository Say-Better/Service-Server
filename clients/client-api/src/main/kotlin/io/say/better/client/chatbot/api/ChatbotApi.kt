package io.say.better.client.chatbot.api

import io.say.better.client.chatbot.config.ChatbotFeignConfiguration
import io.say.better.client.chatbot.dto.ChatbotRequest
import io.say.better.client.chatbot.dto.ChatbotResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    name = "chatbot",
    url = "\${chatbot.api.base-url}",
    configuration = [ChatbotFeignConfiguration::class],
)
interface ChatbotApi {
    @PostMapping("", consumes = ["application/json"], produces = ["application/json"])
    fun chat(
        @RequestBody request: ChatbotRequest,
    ): ChatbotResponse.Response
}
