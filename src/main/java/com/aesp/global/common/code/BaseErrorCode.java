package com.aesp.global.common.code;

import com.aesp.global.common.response.ResponseDto;

public interface BaseErrorCode {

	public ResponseDto.ErrorReasonDto getReason();

	public ResponseDto.ErrorReasonDto getReasonHttpStatus();

}
