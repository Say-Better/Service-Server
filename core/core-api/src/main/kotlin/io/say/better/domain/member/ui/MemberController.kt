package io.say.better.domain.member.ui

import io.say.better.domain.member.application.MemberFacade
import io.say.better.global.common.response.ResponseDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Member", description = "Member API")
@RestController
@RequestMapping("/api/member")
class MemberController(
    private val memberFacade: MemberFacade,
) {
    @GetMapping("/connect/code")
    fun connectCode(): ResponseDto<String> {
        val code = memberFacade.createConnectCode()
        return ResponseDto.onSuccess(code)
    }

    @PostMapping("/connect/{code}")
    fun connect(
        @PathVariable(value = "code") code: String?,
    ): ResponseDto<Nothing?> {
        memberFacade.connect(code)
        return ResponseDto.onSuccess(null)
    }
}
