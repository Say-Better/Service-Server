package io.say.better.domain.member.ui.dto

class AuthRequest private constructor() {
    init {
        throw IllegalStateException("Utility class")
    }

    data class LoginDTO(
        val identityToken: String,
    )

    data class CommonLoginDTO(
        val name: String,
        val email: String,
        val birthDate: String = "",
    )
}
