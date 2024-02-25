package com.saybetter.client.api;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RecommendClient {

	private final RecommendApi recommendApi;

}
