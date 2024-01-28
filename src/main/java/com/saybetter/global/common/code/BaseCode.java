package com.saybetter.global.common.code;

import com.saybetter.global.common.response.ResponseDto;

public interface BaseCode {

	ResponseDto.ReasonDto getReason();

	ResponseDto.ReasonDto getReasonHttpStatus();

}
