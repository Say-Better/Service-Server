package io.say.better.domain.member

import io.say.better.core.common.auth.jwt.JwtToken

const val ACCESS_TOKEN: String = "SOME_ACCESS_TOKEN"
const val REFRESH_TOKEN: String = "SOME_REFRESH_TOKEN"

fun createJwtToken(
    accessToken: String = ACCESS_TOKEN,
    refreshToken: String = REFRESH_TOKEN,
): JwtToken = JwtToken(accessToken, refreshToken)
