package io.say.better.global.common.code.status

import io.say.better.global.common.code.BaseCode
import io.say.better.global.common.response.ResponseDto.ReasonDto
import lombok.AllArgsConstructor
import lombok.Getter
import org.springframework.http.HttpStatus

enum class SuccessStatus(
        private val httpStatus: HttpStatus? = null,
        private val code: String? = null,
        private val message: String? = null
) : BaseCode {
    // Success
    OK(HttpStatus.OK, "SUCCESS_200", "OK");

    override fun reason(status: SuccessStatus): ReasonDto {
        return ReasonDto(
                isSuccess = true,
                code = status.code,
                message = status.message
        )
    }

    override fun reasonHttpStatus(status: SuccessStatus): ReasonDto {
        return ReasonDto(
                httpStatus = status.httpStatus,
                isSuccess = true,
                code = status.code,
                message = status.message
        )
    }

}
