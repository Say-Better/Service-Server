package io.say.better.global.common.code;

import io.say.better.global.common.response.ResponseDto;

public interface BaseErrorCode {

	ResponseDto.ErrorReasonDto getReason();

	ResponseDto.ErrorReasonDto getReasonHttpStatus();

}
