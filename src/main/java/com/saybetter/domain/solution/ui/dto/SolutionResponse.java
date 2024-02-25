package com.saybetter.domain.solution.ui.dto;

import java.util.List;

import lombok.Builder;

public class SolutionResponse {

	private SolutionResponse() {
		throw new IllegalStateException("Utility class");
	}

	@Builder
	public static class SymbolList {
		private String name;
		private List<SymbolInfo> symbols;
	}

	@Builder
	public static class SymbolInfo {
		private String description;
		private String imageUrl;
	}
}
