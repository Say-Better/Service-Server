package io.say.better.global.common.code

import io.say.better.global.common.response.ResponseDto.ErrorReasonDto

interface BaseErrorCode {

    val reason: ErrorReasonDto

    val reasonHttpStatus: ErrorReasonDto
}
