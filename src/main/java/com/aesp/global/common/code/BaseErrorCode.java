package com.aesp.global.common.code;

import com.aesp.global.common.response.ResponseDto;

public interface BaseErrorCode {

	ResponseDto.ErrorReasonDto getReason();

	ResponseDto.ErrorReasonDto getReasonHttpStatus();

}
