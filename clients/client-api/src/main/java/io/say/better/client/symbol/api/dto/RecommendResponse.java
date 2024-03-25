package io.say.better.client.symbol.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RecommendResponse {

	private RecommendResponse() {
		throw new IllegalStateException("Utility class");
	}

	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SymbolRecommend {
		String symbol;
	}

}
