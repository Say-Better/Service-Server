package io.say.better.global.common.response

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import lombok.ToString
import java.time.LocalDateTime

@Getter
@Builder
@ToString
@AllArgsConstructor
class JwtTokenResponseDto {
    private val accessToken: String? = null
    private val refreshToken: String? = null
    private val expiredTime: LocalDateTime? = null

    @JsonProperty("isExisted")
    private val isExisted = false
}
