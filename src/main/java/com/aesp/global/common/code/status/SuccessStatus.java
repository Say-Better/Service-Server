package com.aesp.global.common.code.status;

import org.springframework.http.HttpStatus;

import com.aesp.global.common.code.BaseCode;
import com.aesp.global.common.response.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

	// Success
	OK(HttpStatus.OK, "SUCCESS_200", "OK"),

	;

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	@Override
	public ResponseDto.ReasonDto getReason() {
		return ResponseDto.ReasonDto.builder()
				.isSuccess(true)
				.code(this.code)
				.message(this.message)
				.build();
	}

	@Override
	public ResponseDto.ReasonDto getReasonHttpStatus() {
		return ResponseDto.ReasonDto.builder()
				.httpStatus(this.httpStatus)
				.isSuccess(true)
				.code(this.code)
				.message(this.message)
				.build();
	}
}
