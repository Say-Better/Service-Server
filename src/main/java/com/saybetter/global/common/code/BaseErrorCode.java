package com.saybetter.global.common.code;

import com.saybetter.global.common.response.ResponseDto;

public interface BaseErrorCode {

	ResponseDto.ErrorReasonDto getReason();

	ResponseDto.ErrorReasonDto getReasonHttpStatus();

}
