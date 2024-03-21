package io.say.better.global.auth.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.extern.slf4j.Slf4j
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component
import java.io.IOException

@Slf4j
@Component
class OAuth2LoginFailureHandler : AuthenticationFailureHandler {
    @Throws(IOException::class)
    override fun onAuthenticationFailure(
            request: HttpServletRequest,
            response: HttpServletResponse,
            exception: AuthenticationException
    ) {
        response.status = HttpServletResponse.SC_BAD_REQUEST
        response.writer.write("소셜 로그인 실패! 서버 로그를 확인해주세요.")
        OAuth2LoginFailureHandler.log.info("소셜 로그인에 실패했습니다. 에러 메시지 : {}", exception.message)
    }
}
