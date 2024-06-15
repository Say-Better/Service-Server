package io.say.better.core.common.code

import io.say.better.core.common.response.ResponseDto.ErrorReasonDto

interface BaseErrorCode {
    val reason: ErrorReasonDto

    val reasonHttpStatus: ErrorReasonDto
}
