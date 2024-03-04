package com.saybetter.global.common.exception;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.saybetter.global.common.code.status.ErrorStatus;
import com.saybetter.global.common.response.ResponseDto;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<Object> validation(
			ConstraintViolationException violationException,
			@Nullable WebRequest request
	) {
		String errorMessage = violationException.getConstraintViolations().stream()
				.map(ConstraintViolation::getMessage)
				.findFirst()
				.orElseThrow(() -> new RuntimeException("ConstraintViolationException 추출 도중 에러 발생"));

		return handleExceptionInternalConstraint(
				violationException,
				ErrorStatus.valueOf(errorMessage),
				HttpHeaders.EMPTY,
				request);
	}

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException notValidException,
			@Nullable HttpHeaders headers,
			@Nullable HttpStatusCode status,
			@Nullable WebRequest request
	) {
		Map<String, String> errors = new LinkedHashMap<>();

		notValidException.getBindingResult().getFieldErrors()
				.forEach(fieldError -> {
					String fieldName = fieldError.getField();
					String errorMessage = Optional.ofNullable(fieldError.getDefaultMessage()).orElse("");
					errors.merge(fieldName, errorMessage,
							(existingErrorMessage, newErrorMessage) -> existingErrorMessage + ", " + newErrorMessage);
				});

		return handleExceptionInternalArgs(
				notValidException,
				HttpHeaders.EMPTY,
				ErrorStatus.valueOf("_BAD_REQUEST"),
				request,
				errors);
	}

	@ExceptionHandler
	public ResponseEntity<Object> exception(
			Exception exception,
			WebRequest request
	) {
		log.error("Exception 발생", exception);

		return handleExceptionInternalFalse(
				exception,
				ErrorStatus.INTERNAL_SERVER_ERROR,
				HttpHeaders.EMPTY,
				ErrorStatus.INTERNAL_SERVER_ERROR.getHttpStatus(),
				request,
				exception.getMessage());
	}

	@ExceptionHandler(value = GeneralException.class)
	public ResponseEntity<Object> onThrowException(
			GeneralException generalException,
			HttpServletRequest request
	) {
		ResponseDto.ErrorReasonDto errorReasonHttpStatus = generalException.getErrorReasonHttpStatus();

		return handleExceptionInternal(
				generalException,
				errorReasonHttpStatus,
				null,
				request);
	}

	private ResponseEntity<Object> handleExceptionInternal(
			Exception exception,
			ResponseDto.ErrorReasonDto reason,
			@Nullable HttpHeaders headers,
			HttpServletRequest request
	) {
		ResponseDto<Object> body = ResponseDto.onFailure(reason.getCode(), reason.getMessage(), null);

		WebRequest webRequest = new ServletWebRequest(request);
		return super.handleExceptionInternal(
				exception,
				body,
				headers,
				reason.getHttpStatus(),
				webRequest
		);
	}

	private ResponseEntity<Object> handleExceptionInternalFalse(
			Exception exception,
			ErrorStatus errorCommonStatus,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request,
			String errorPoint
	) {
		ResponseDto<Object> body = ResponseDto.onFailure(
				errorCommonStatus.getCode(),
				errorCommonStatus.getMessage(),
				errorPoint);

		return super.handleExceptionInternal(
				exception,
				body,
				headers,
				status,
				request);
	}

	private ResponseEntity<Object> handleExceptionInternalArgs(
			Exception exception,
			HttpHeaders headers,
			ErrorStatus errorCommonStatus,
			WebRequest request,
			Map<String, String> errorArgs
	) {
		ResponseDto<Object> body = ResponseDto.onFailure(
				errorCommonStatus.getCode(),
				errorCommonStatus.getMessage(),
				errorArgs);

		return super.handleExceptionInternal(
				exception,
				body,
				headers,
				errorCommonStatus.getHttpStatus(),
				request);
	}

	private ResponseEntity<Object> handleExceptionInternalConstraint(
			Exception exception,
			ErrorStatus errorCommonStatus,
			HttpHeaders headers,
			WebRequest request
	) {
		ResponseDto<Object> body = ResponseDto.onFailure(
				errorCommonStatus.getCode(),
				errorCommonStatus.getMessage(),
				null);

		return super.handleExceptionInternal(
				exception,
				body,
				headers,
				errorCommonStatus.getHttpStatus(),
				request
		);
	}
}
