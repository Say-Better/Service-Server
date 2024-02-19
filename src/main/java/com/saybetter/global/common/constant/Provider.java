package com.saybetter.global.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Provider {
	GOOGLE("google"),
	NAVER("naver"),
	KAKAO("kakao"),
	FACEBOOK("facebook");

	private final String providerName;
}
