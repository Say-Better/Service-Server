package io.say.better.core.common.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

class JwtTokenResponseDto constructor(
    private val accessToken: String? = null,
    private val refreshToken: String? = null,
    private val expiredTime: LocalDateTime? = null,
    @field:JsonProperty("isExisted")
    private val isExisted: Boolean = false,
)
