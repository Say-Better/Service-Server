package io.say.better.core.enums.auth.jwt

data class JwtToken (
    val accessToken: String,
    val refreshToken: String,
)
