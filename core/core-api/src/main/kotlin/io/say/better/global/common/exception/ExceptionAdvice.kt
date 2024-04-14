package io.say.better.global.common.exception

import io.say.better.global.common.code.status.ErrorStatus
import io.say.better.global.common.response.ResponseDto
import io.say.better.global.common.response.ResponseDto.ErrorReasonDto
import io.say.better.global.config.logger.logger
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolation
import jakarta.validation.ConstraintViolationException
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*
import java.util.function.Consumer

@Slf4j
@RestControllerAdvice(annotations = [RestController::class])
class ExceptionAdvice : ResponseEntityExceptionHandler() {

    private val log = logger()

    @ExceptionHandler
    fun validation(
        violationException: ConstraintViolationException,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errorMessage = violationException.constraintViolations.stream()
            .map { obj: ConstraintViolation<*> -> obj.message }
            .findFirst()
            .orElseThrow { RuntimeException("ConstraintViolationException 추출 도중 에러 발생") }

        return handleExceptionInternalConstraint(
            violationException,
            ErrorStatus.valueOf(errorMessage),
            HttpHeaders.EMPTY,
            request
        )
    }

    public override fun handleMethodArgumentNotValid(
        notValidException: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errors: MutableMap<String, String> = LinkedHashMap()

        notValidException.bindingResult.fieldErrors
            .forEach(Consumer { fieldError: FieldError ->
                val fieldName = fieldError.field
                val errorMessage = Optional.ofNullable(fieldError.defaultMessage).orElse("")
                errors.merge(
                    fieldName, errorMessage
                ) { existingErrorMessage: String, newErrorMessage: String -> "$existingErrorMessage, $newErrorMessage" }
            })

        return handleExceptionInternalArgs(
            notValidException,
            HttpHeaders.EMPTY,
            ErrorStatus.valueOf("_BAD_REQUEST"),
            request,
            errors
        )
    }

    @ExceptionHandler
    fun exception(
        exception: Exception,
        request: WebRequest
    ): ResponseEntity<Any>? {
        log.error("Exception 발생", exception)

        return handleExceptionInternalFalse(
            exception,
            ErrorStatus.INTERNAL_SERVER_ERROR,
            HttpHeaders.EMPTY,
            ErrorStatus.INTERNAL_SERVER_ERROR.httpStatus,
            request,
            exception.message
        )
    }

    @ExceptionHandler(value = [GeneralException::class])
    fun onThrowException(
        generalException: GeneralException,
        request: HttpServletRequest
    ): ResponseEntity<Any>? {
        val errorReasonHttpStatus = generalException.errorReasonHttpStatus

        return handleExceptionInternal(
            exception = generalException,
            reason = errorReasonHttpStatus,
            headers = HttpHeaders.EMPTY,
            request = request
        )
    }

    private fun handleExceptionInternal(
        exception: Exception,
        reason: ErrorReasonDto?,
        headers: HttpHeaders?,
        request: HttpServletRequest
    ): ResponseEntity<Any>? {
        val body: ResponseDto<Any?> = ResponseDto.onFailure(
            code = reason!!.code,
            message = reason.message,
            result = null
        )

        val webRequest: WebRequest = ServletWebRequest(request)
        return super.handleExceptionInternal(
            exception,
            body,
            headers!!,
            reason.httpStatus!!,
            webRequest
        )
    }

    private fun handleExceptionInternalFalse(
        exception: Exception,
        errorCommonStatus: ErrorStatus,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest,
        errorPoint: String?
    ): ResponseEntity<Any>? {
        val body: ResponseDto<Any?> = ResponseDto.onFailure(
            errorCommonStatus.code,
            errorCommonStatus.message,
            errorPoint
        )

        return super.handleExceptionInternal(
            exception,
            body,
            headers,
            status,
            request
        )
    }

    private fun handleExceptionInternalArgs(
        exception: Exception,
        headers: HttpHeaders,
        errorCommonStatus: ErrorStatus,
        request: WebRequest,
        errorArgs: Map<String, String>
    ): ResponseEntity<Any>? {
        val body: ResponseDto<Any> = ResponseDto.onFailure(
            errorCommonStatus.code,
            errorCommonStatus.message,
            errorArgs
        )

        return super.handleExceptionInternal(
            exception,
            body,
            headers,
            errorCommonStatus.httpStatus,
            request
        )
    }

    private fun handleExceptionInternalConstraint(
        exception: Exception,
        errorCommonStatus: ErrorStatus,
        headers: HttpHeaders,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val body: ResponseDto<Any?> = ResponseDto.onFailure(
            errorCommonStatus.code,
            errorCommonStatus.message,
            null
        )

        return super.handleExceptionInternal(
            exception,
            body,
            headers,
            errorCommonStatus.httpStatus,
            request
        )
    }
}
