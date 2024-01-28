package com.saybetter.domain.temp.converter;

import com.saybetter.domain.temp.dto.TempResponse;

public class TempResponseConverter {
	public static TempResponse.ExceptionTestDto toExceptionTestDto(String message) {
		return TempResponse.ExceptionTestDto.builder()
				.message(message)
				.build();
	}
}
