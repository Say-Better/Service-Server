package io.say.better.core.common.exception

import io.say.better.core.common.code.BaseErrorCode
import io.say.better.core.common.response.ResponseDto.ErrorReasonDto

open class GeneralException(
    private val code: BaseErrorCode,
) : RuntimeException() {
    val errorReasonDto: ErrorReasonDto
        get() = this.code.reason

    val errorReasonHttpStatus: ErrorReasonDto
        get() = this.code.reasonHttpStatus
}
