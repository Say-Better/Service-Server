package com.aesp.global.common.code;

import com.aesp.global.common.response.ResponseDto;

public interface BaseCode {

	ResponseDto.ReasonDto getReason();

	ResponseDto.ReasonDto getReasonHttpStatus();

}
