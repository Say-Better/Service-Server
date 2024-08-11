package io.say.better.domain.chatting.ui

import io.say.better.core.common.response.ResponseDto
import io.say.better.domain.chatting.application.ChattingFacade
import io.say.better.domain.chatting.ui.dto.ChattingRequest
import io.say.better.domain.chatting.ui.dto.ChattingResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/chatting")
class ChattingController(
    private val chattingFacade: ChattingFacade,
) {
    @PostMapping("")
    fun chat(
        @RequestBody request: ChattingRequest,
    ): ResponseDto<ChattingResponse> {
        val result = chattingFacade.chat(request)
        return ResponseDto.onSuccess(result)
    }
}
