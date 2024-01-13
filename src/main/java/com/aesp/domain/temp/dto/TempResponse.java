package com.aesp.domain.temp.dto;

import lombok.Builder;
import lombok.Getter;

public class TempResponse {

	@Getter
	@Builder
	public static class ExceptionTestDto {
		private String message;
	}

}
