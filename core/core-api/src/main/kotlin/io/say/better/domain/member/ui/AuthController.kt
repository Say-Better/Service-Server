package io.say.better.domain.member.ui

import io.say.better.core.common.constant.AppType
import io.say.better.core.common.constant.Provider
import io.say.better.core.common.constant.RoleType
import io.say.better.core.common.response.ResponseDto
import io.say.better.domain.member.application.AuthFacade
import io.say.better.domain.member.ui.dto.AuthRequest
import io.say.better.domain.member.ui.dto.AuthResponse.LoginDTO
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Auth", description = "Auth API")
@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authFacade: AuthFacade,
) {
    @PostMapping("/login/{appType}/{socialType}")
    fun login(
        @PathVariable appType: AppType,
        @PathVariable socialType: Provider,
        @RequestBody request: AuthRequest.LoginDTO,
    ): ResponseDto<LoginDTO> = ResponseDto.onSuccess(authFacade.login(appType, socialType, request))

    @PostMapping("/assign/educator")
    fun assignEducator(): ResponseDto<Nothing?> {
        val role = RoleType.EDUCATOR
        authFacade.assignUserRole(role)
        return ResponseDto.onSuccess(null)
    }

    @PostMapping("/assign/learner")
    fun assignLearner(): ResponseDto<Nothing?> {
        val role = RoleType.LEARNER
        authFacade.assignUserRole(role)
        return ResponseDto.onSuccess(null)
    }
}
