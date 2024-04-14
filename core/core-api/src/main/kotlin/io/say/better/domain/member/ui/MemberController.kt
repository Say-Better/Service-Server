package io.say.better.domain.member.ui

import io.say.better.domain.member.application.MemberFacade
import io.say.better.global.common.response.ResponseDto
import io.swagger.v3.oas.annotations.tags.Tag
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*

@Tag(name = "Member", description = "Member API")
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
class MemberController(
    private val memberFacade: MemberFacade
) {

    @GetMapping("/connect/code")
    fun connectCode(): ResponseDto<String> {
        val code = memberFacade!!.createConnectCode()
        return ResponseDto.onSuccess(code)
    }

    @PostMapping("/connect/{code}")
    fun connect(@PathVariable(value = "code") code: String?): ResponseDto<Void?> {
        memberFacade!!.connect(code)
        return ResponseDto.onSuccess(null)
    }
}
