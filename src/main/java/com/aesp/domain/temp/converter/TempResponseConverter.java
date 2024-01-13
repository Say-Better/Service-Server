package com.aesp.domain.temp.converter;

import com.aesp.domain.temp.dto.TempResponse;

public class TempResponseConverter {
	public static TempResponse.ExceptionTestDto toExceptionTestDto(String message) {
		return TempResponse.ExceptionTestDto.builder()
				.message(message)
				.build();
	}
}
