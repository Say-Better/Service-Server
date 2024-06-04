package io.say.better.domain.member.application.converter

import io.say.better.core.enums.auth.jwt.JwtToken
import io.say.better.domain.member.ui.dto.AuthResponse
import io.say.better.storage.mysql.domain.entity.Member

class AuthResponseConverter private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun toLoginDTO(
            member: Member,
            token: JwtToken,
        ): AuthResponse.LoginDTO {
            return AuthResponse.LoginDTO(
                member.memberId!!,
                token.accessToken,
                token.refreshToken,
            )
        }
    }
}
