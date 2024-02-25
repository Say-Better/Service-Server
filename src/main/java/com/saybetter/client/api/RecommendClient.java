package com.saybetter.client.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.saybetter.client.api.dto.RecommendRequest;
import com.saybetter.client.api.dto.RecommendResult;
import com.saybetter.client.converter.RecommendResultConverter;

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
