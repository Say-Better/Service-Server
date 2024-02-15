package com.saybetter.domain.temp.ui.dto;

import lombok.Builder;
import lombok.Getter;

public class TempResponse {

	@Getter
	@Builder
	public static class ExceptionTestDto {
		private String message;
	}

}
