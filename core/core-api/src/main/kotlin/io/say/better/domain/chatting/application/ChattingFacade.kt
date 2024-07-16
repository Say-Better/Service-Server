package io.say.better.domain.chatting.application

import io.say.better.client.chatbot.client.ChatbotClient
import io.say.better.client.chatbot.dto.ChatbotRequest
import io.say.better.client.chatbot.dto.ChatbotResponse
import io.say.better.domain.chatting.application.converter.ChattingResponseConverter
import io.say.better.domain.chatting.ui.dto.ChattingRequest
import io.say.better.domain.member.application.impl.MemberService
import io.say.better.global.advice.Tx
import org.springframework.stereotype.Component

@Component
class ChattingFacade(
    private val chatbotClient: ChatbotClient,
    private val memberService: MemberService,
) {
    fun chat(request: ChattingRequest) =
        Tx.readable {
            val member = memberService.currentMember()
            val response: ChatbotResponse.Response = chatbotClient.chat(ChatbotRequest(request.sentence, member.email))
            return@readable ChattingResponseConverter.toChattingResponse(response)
        }
}
