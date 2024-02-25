package com.saybetter.domain.solution.ui.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SolutionRequest {

	private SolutionRequest() {
		throw new IllegalStateException("Utility class");
	}

	@Getter
	@AllArgsConstructor
	public static class CreateSolution {
		private String title;
		private Integer baselineSessionTime;
		private Double interCompleteThresh;
		private Integer interMaintainTerm;
		private Integer commOptTimes;
		private Integer commOptCnt;
		private List<String> symbols;
	}
}
