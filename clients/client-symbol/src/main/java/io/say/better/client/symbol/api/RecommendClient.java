package io.say.better.client.symbol.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.say.better.client.symbol.api.dto.RecommendRequest;
import io.say.better.client.symbol.api.dto.RecommendResult;
import io.say.better.client.symbol.converter.RecommendResultConverter;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RecommendClient {

	private final RecommendApi recommendApi;

	@Value("${recommend.api.identity-token}")
	private String identityToken;

	public RecommendResult.SymbolRecommend recommend(String requestParameter) {
		RecommendRequest.SymbolRecommend request = RecommendRequest.SymbolRecommend.builder()
				.name(requestParameter)
				.build();

		return RecommendResultConverter.toSymbolRecommend(
				recommendApi.recommend(identityToken, request)
		);
	}
}
