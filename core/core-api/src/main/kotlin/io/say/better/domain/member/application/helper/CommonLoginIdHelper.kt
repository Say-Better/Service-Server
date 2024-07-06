package io.say.better.domain.member.application.helper

import io.say.better.core.common.code.status.ErrorStatus
import io.say.better.core.common.constant.Provider
import io.say.better.domain.member.exception.AuthException
import org.springframework.stereotype.Component

/**
 * 일반 로그인에 대한 LoginId를 발급 및 해석을 위한 클래스 입니다.
 */
@Component
class CommonLoginIdHelper(
    private val emailEncoderHelper: EmailEncoderHelper,
) {
    companion object {
        private const val PREFIX = "common-"
    }

    private val commonProvider = Provider.COMMON.toString().lowercase()

    fun getLoginId(email: String): String = "$commonProvider-${emailEncoderHelper.encoder(email)}"

    fun toEmail(loginId: String): String {
        if (!loginId.startsWith(PREFIX)) throw AuthException(ErrorStatus.FAILED_LOGIN_ID_TO_EMAIL)
        return loginId.substring(PREFIX.length)
    }
}
