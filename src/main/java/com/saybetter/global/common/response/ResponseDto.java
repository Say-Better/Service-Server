package com.saybetter.global.common.response;

import org.springframework.http.HttpStatus;

import com.saybetter.global.common.code.BaseCode;
import com.saybetter.global.common.code.status.SuccessStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ResponseDto<T> {

	@JsonProperty("isSuccess")
	private final Boolean isSuccess;
	private final String code;
	private final String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final T result;

	// 성공한 경우
	public static <T> ResponseDto<T> onSuccess(T result) {
		return new ResponseDto<>(
				true,
				SuccessStatus.OK.getCode(),
				SuccessStatus.OK.getMessage(),
				result
		);
	}

	public static <T> ResponseDto<T> of(BaseCode code, T result) {
		return new ResponseDto<>(
				true,
				code.getReasonHttpStatus().getCode(),
				code.getReasonHttpStatus().getMessage(),
				result
		);
	}

	// 실패한 경우
	public static <T> ResponseDto<T> onFailure(String code, String message, T result) {
		return new ResponseDto<>(
				false,
				code,
				message,
				result
		);
	}

	@Getter
	@Builder
	public static class ErrorReasonDto {
		private HttpStatus httpStatus;
		private final boolean isSuccess;
		private final String code;
		private final String message;
	}

	@Getter
	@Builder
	public static class ReasonDto {
		private HttpStatus httpStatus;
		private final boolean isSuccess;
		private final String code;
		private final String message;
	}
}
