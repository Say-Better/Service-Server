package com.saybetter.client.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;

public class RecommendResult {

	private RecommendResult() {
		throw new IllegalStateException("Utility class");
	}

	@Builder
	@AllArgsConstructor
	public static class SymbolRecommend {
		private List<String> symbols;
	}
}
