package io.say.better.client.symbol.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class RecommendResult {

	private RecommendResult() {
		throw new IllegalStateException("Utility class");
	}

	@Getter
	@Builder
	@AllArgsConstructor
	public static class SymbolRecommend {
		private List<String> symbols;
	}
}
