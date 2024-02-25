package com.saybetter.client.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.saybetter.client.api.dto.RecommendRequest;
import com.saybetter.client.api.dto.RecommendResponse;

@FeignClient(name = "recommend", url = "${recommend.api.base-url}")
public interface RecommendApi {

	@PostMapping(value = "/recommend", consumes = MediaType.APPLICATION_JSON_VALUE)
	RecommendResponse.SymbolRecommend recommend(
			@RequestHeader("Authorization") String identityToken,
			@RequestBody RecommendRequest.SymbolRecommend request
	);

}
