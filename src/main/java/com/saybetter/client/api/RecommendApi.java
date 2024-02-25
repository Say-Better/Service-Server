package com.saybetter.client.api;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "recommend", url = "${recommend.api.base-url}")
public interface RecommendApi {
}

