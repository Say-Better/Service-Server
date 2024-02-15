package com.saybetter.domain.temp.application.converter;

import com.saybetter.domain.temp.ui.dto.TempResponse;

public class TempResponseConverter {
	public static TempResponse.ExceptionTestDto toExceptionTestDto(String message) {
		return TempResponse.ExceptionTestDto.builder()
				.message(message)
				.build();
	}
}
