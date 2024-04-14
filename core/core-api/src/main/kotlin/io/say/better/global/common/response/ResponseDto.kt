package io.say.better.global.common.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import io.say.better.global.common.code.BaseCode
import io.say.better.global.common.code.status.SuccessStatus
import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Getter
import org.springframework.http.HttpStatus

@Getter
@AllArgsConstructor
@JsonPropertyOrder("isSuccess", "code", "message", "result")
data class ResponseDto<T>(
        @field:JsonProperty("isSuccess")
        private val isSuccess: Boolean,
        @field:JsonProperty("code")
        private val code: String,
        @field:JsonProperty("message")
        private val message: String,

        @field:JsonInclude(JsonInclude.Include.NON_NULL)
        @field:JsonProperty("result")
        private val result: T,
) {

    data class ErrorReasonDto(
            val httpStatus: HttpStatus? = null,
            val isSuccess: Boolean = false,
            val code: String,
            val message: String
    )

    data class ReasonDto(
            val httpStatus: HttpStatus? = null,
            val isSuccess: Boolean = false,
            val code: String,
            val message: String
    )

    companion object {
        // 성공한 경우
        fun <T> onSuccess(result: T): ResponseDto<T> {
            return ResponseDto(
                    isSuccess = true,
                    code = SuccessStatus.OK.code,
                    message = SuccessStatus.OK.message,
                    result = result
            )
        }

        fun <T> of(code: BaseCode, result: T): ResponseDto<T> {
            return ResponseDto(
                    isSuccess = true,
                    code = code.reasonHttpStatus.code,
                    message = code.reasonHttpStatus.message,
                    result = result
            )
        }

        // 실패한 경우
        fun <T> onFailure(code: String, message: String, result: T): ResponseDto<T> {
            return ResponseDto(
                    isSuccess = false,
                    code = code,
                    message = message,
                    result = result
            )
        }
    }
}
