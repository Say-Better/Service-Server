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
        private val code: String,
        private val message: String,

        @field:JsonInclude(JsonInclude.Include.NON_NULL)
        private val result: T,
) {

    data class ErrorReasonDto(
            private val httpStatus: HttpStatus? = null,
            private val isSuccess: Boolean = false,
            private val code: String? = null,
            private val message: String? = null
    )

    data class ReasonDto(
            private val httpStatus: HttpStatus? = null,
            private val isSuccess: Boolean = false,
            private val code: String? = null,
            private val message: String? = null
    )

    companion object {
        // 성공한 경우
        fun <T> onSuccess(result: T): ResponseDto<T> {
            return ResponseDto(
                    isSuccess = true,
                    code = SuccessStatus.OK.code.toString(),
                    message = SuccessStatus.OK.message.toString(),
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
        fun <T> onFailure(code: String?, message: String?, result: T): ResponseDto<T> {
            return ResponseDto(
                    isSuccess = false,
                    code = code,
                    message = message,
                    result = result
            )
        }
    }
}
