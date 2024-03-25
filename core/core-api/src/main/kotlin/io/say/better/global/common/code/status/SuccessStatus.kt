package io.say.better.global.common.code.status

import io.say.better.global.common.code.BaseCode
import io.say.better.global.common.response.ResponseDto.ReasonDto
import org.springframework.http.HttpStatus

enum class SuccessStatus(
    val httpStatus: HttpStatus? = null,
    val code: String,
    val message: String
) : BaseCode {
    // Success
    OK(HttpStatus.OK, "SUCCESS_200", "OK");

    override val reason: ReasonDto
        get() = ReasonDto(
            isSuccess = true,
            code = this.code,
            message = this.message
        )


    override val reasonHttpStatus: ReasonDto
        get() = ReasonDto(
            httpStatus = this.httpStatus,
            isSuccess = true,
            code = this.code,
            message = this.message
        )

}
