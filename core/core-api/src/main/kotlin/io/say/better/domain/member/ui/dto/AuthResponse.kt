package io.say.better.domain.member.ui.dto

class AuthResponse private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    data class LoginDTO (
        val memberId: Long,
        val accessToken: String,
        val refreshToken: String,
    )
}
