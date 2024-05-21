package io.say.better.global.auth.handler

import io.say.better.global.auth.CustomOAuth2User
import io.say.better.global.config.logger.logger
import io.say.better.global.jwt.service.JwtService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2LoginSuccessHandler(
    private val jwtService: JwtService,
) : AuthenticationSuccessHandler {
    private val log = logger()

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ) {
        log.info("OAuth2 Login 성공!")
        val oAuth2User = authentication.principal as CustomOAuth2User
        loginSuccess(response, oAuth2User) // 로그인에 성공한 경우 access, refresh 토큰 생성
    }

    private fun loginSuccess(
        response: HttpServletResponse,
        oAuth2User: CustomOAuth2User,
    ) {
        val accessToken = jwtService.createAccessToken(oAuth2User.email)
        val refreshToken = jwtService.createRefreshToken()
        response.addHeader(jwtService.jwtProperties.accessHeader, "Bearer $accessToken")
        response.addHeader(jwtService.jwtProperties.refreshHeader, "Bearer $refreshToken")

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken)
        jwtService.updateRefreshToken(oAuth2User.email, refreshToken)
    }
}
