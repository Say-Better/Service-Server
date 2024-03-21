package io.say.better.global.common.code

import io.say.better.global.common.code.status.ErrorStatus
import io.say.better.global.common.response.ResponseDto.ErrorReasonDto

interface BaseErrorCode {
    fun reason(status: ErrorStatus): ErrorReasonDto

    fun reasonHttpStatus(status: ErrorStatus): ErrorReasonDto
}
