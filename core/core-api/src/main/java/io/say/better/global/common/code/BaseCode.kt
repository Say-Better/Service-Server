package io.say.better.global.common.code

import io.say.better.global.common.code.status.SuccessStatus
import io.say.better.global.common.response.ResponseDto.ReasonDto

interface BaseCode {

    val reason: ReasonDto

    val reasonHttpStatus: ReasonDto
}
