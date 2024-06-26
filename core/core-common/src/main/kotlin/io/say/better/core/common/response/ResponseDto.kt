package io.say.better.core.common.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import io.say.better.core.common.code.BaseCode
import io.say.better.core.common.code.status.SuccessStatus.OK
import org.springframework.http.HttpStatus

@JsonPropertyOrder("isSuccess", "code", "message", "result")
data class ResponseDto<T>(
    @field:JsonProperty("isSuccess")
    private val isSuccess: Boolean,
    @field:JsonProperty("code")
    private val code: String,
    @field:JsonProperty("message")
    private val message: String,
    @field:JsonProperty("result")
    private val result: T,
) {
    data class ErrorReasonDto(
        val httpStatus: HttpStatus? = null,
        val isSuccess: Boolean = false,
        val code: String,
        val message: String,
    )

    data class ReasonDto(
        val httpStatus: HttpStatus? = null,
        val isSuccess: Boolean = false,
        val code: String,
        val message: String,
    )

    companion object {
        // 성공한 경우
        fun <T> onSuccess(result: T): ResponseDto<T> =
            ResponseDto(
                isSuccess = true,
                code = OK.code,
                message = OK.message,
                result = result,
            )

        fun <T> of(
            code: BaseCode,
            result: T,
        ): ResponseDto<T> =
            ResponseDto(
                isSuccess = true,
                code = code.reasonHttpStatus.code,
                message = code.reasonHttpStatus.message,
                result = result,
            )

        // 실패한 경우
        fun <T> onFailure(
            code: String,
            message: String,
            result: T,
        ): ResponseDto<T> =
            ResponseDto(
                isSuccess = false,
                code = code,
                message = message,
                result = result,
            )
    }
}
