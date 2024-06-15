package io.say.better.core.common.code

import io.say.better.core.common.response.ResponseDto.ReasonDto

interface BaseCode {
    val reason: ReasonDto

    val reasonHttpStatus: ReasonDto
}
