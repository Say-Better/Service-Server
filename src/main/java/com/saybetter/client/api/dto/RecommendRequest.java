package com.saybetter.client.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

public class RecommendRequest {

	private RecommendRequest() {
		throw new IllegalStateException("Utility class");
	}

	@Builder
	@AllArgsConstructor
	public static class SymbolRecommend {
		private String name;
	}
}
