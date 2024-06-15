package io.say.better.core.common.auth.jwt

data class JwtToken(
    val accessToken: String,
    val refreshToken: String,
)
