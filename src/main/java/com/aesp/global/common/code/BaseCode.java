package com.aesp.global.common.code;

import com.aesp.global.common.response.ResponseDto;

public interface BaseCode {

	public ResponseDto.ReasonDto getReason();

	public ResponseDto.ReasonDto getReasonHttpStatus();

}
