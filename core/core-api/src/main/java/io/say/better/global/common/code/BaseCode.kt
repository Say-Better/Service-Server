package io.say.better.global.common.code;

import io.say.better.global.common.response.ResponseDto;

public interface BaseCode {

	ResponseDto.ReasonDto getReason();

	ResponseDto.ReasonDto getReasonHttpStatus();

}
