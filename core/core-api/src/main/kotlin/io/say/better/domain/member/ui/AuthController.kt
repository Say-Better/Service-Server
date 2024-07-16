package io.say.better.domain.member.ui

import io.say.better.core.common.constant.AppType
import io.say.better.core.common.constant.Provider
import io.say.better.core.common.constant.RoleType
import io.say.better.core.common.response.ResponseDto
import io.say.better.domain.member.application.AuthFacade
import io.say.better.domain.member.ui.dto.AuthRequest
import io.say.better.domain.member.ui.dto.AuthResponse
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
    ): ResponseDto<AuthResponse.LoginDTO> = ResponseDto.onSuccess(authFacade.login(appType, socialType, request))

    @PostMapping("/login/{appType}/common")
    fun login(
        @PathVariable appType: AppType,
        @RequestBody request: AuthRequest.CommonLoginDTO,
    ): ResponseDto<AuthResponse.LoginDTO> = ResponseDto.onSuccess(authFacade.login(appType, request))

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
