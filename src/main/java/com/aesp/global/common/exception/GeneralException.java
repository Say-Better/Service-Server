package com.aesp.global.common.exception;

import com.aesp.global.common.code.BaseErrorCode;
import com.aesp.global.common.response.ResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

	private final BaseErrorCode code;

	public ResponseDto.ErrorReasonDto getErrorReasonDto() {
		return this.code.getReason();
	}

	public ResponseDto.ErrorReasonDto getErrorReasonHttpStatus() {
		return this.code.getReasonHttpStatus();
	}
}
