package io.say.better.domain.member.application.converter

import io.say.better.core.common.auth.jwt.JwtToken
import io.say.better.domain.member.ui.dto.AuthResponse
import io.say.better.storage.mysql.domains.account.entity.Member

class AuthResponseConverter private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    companion object {
        fun toLoginDTO(
            member: Member,
            token: JwtToken,
            needMemberInfo: Boolean,
        ): AuthResponse.LoginDTO =
            AuthResponse.LoginDTO(
                member.memberId!!,
                token.accessToken,
                token.refreshToken,
                needMemberInfo,
            )
    }
}
