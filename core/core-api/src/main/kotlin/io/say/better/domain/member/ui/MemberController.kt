package io.say.better.domain.member.ui

import io.say.better.core.common.response.ResponseDto
import io.say.better.domain.member.application.MemberFacade
import io.say.better.domain.member.ui.dto.MemberRequest
import io.say.better.domain.member.ui.dto.MemberResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

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
        @PathVariable(value = "code") code: String,
    ): ResponseDto<Boolean> = ResponseDto.onSuccess(memberFacade.connect(code))

    @GetMapping("/educator/info")
    fun getEducatorInfo(): ResponseDto<MemberResponse.EducatorDTO> {
        val member = memberFacade.getEducatorInfo()
        return ResponseDto.onSuccess(member)
    }

    @GetMapping("/learner/info")
    fun getLearnerInfo(): ResponseDto<MemberResponse.LearnerDTO> {
        val member = memberFacade.getLearnerInfo()
        return ResponseDto.onSuccess(member)
    }

    @PostMapping("/educator/info", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun postEducatorInfo(
        @RequestPart(name = "file") file: MultipartFile,
        @RequestParam("name") name: String,
    ): ResponseDto<String> {
        memberFacade.postEducatorInfo(file, name)

        return ResponseDto.onSuccess("Success")
    }

    @PostMapping("/learner/info", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun postLearnerInfo(
        @RequestPart(name = "file") file: MultipartFile,
        @RequestPart(name = "dto") request: MemberRequest.LearnerInitialInfoDTO,
    ): ResponseDto<String> {
        memberFacade.postLearnerInfo(file, request)

        return ResponseDto.onSuccess("Success")
    }
}
